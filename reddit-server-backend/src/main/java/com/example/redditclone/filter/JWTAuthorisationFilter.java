package com.example.redditclone.filter;

import com.example.redditclone.model.BlacklistTokens;
import com.example.redditclone.repository.BlackListTokenRepository;
import com.example.redditclone.service.JwtProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JWTAuthorisationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JWTAuthorisationFilter.class);

    @Autowired
    private JwtProviderService jwtProviderService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BlackListTokenRepository blackListTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String jwt = null;
        String username = null;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwt = authorization.substring(7);
            username = jwtProviderService.extractUsername(jwt);
        }

        if (isBlacklistToken(jwt)) {
            log.error(" BlackListed token found");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtProviderService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernameAuthentication = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()
                );
                usernameAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernameAuthentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isBlacklistToken(String jwt) {
        Optional<BlacklistTokens> jwtToken = blackListTokenRepository.findByJwtToken(jwt);
        if (jwtToken.isPresent())
            return true;
        return false;
    }
}
