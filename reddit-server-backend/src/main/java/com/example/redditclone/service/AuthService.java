package com.example.redditclone.service;

import com.example.redditclone.builder.MailContentBuilder;
import com.example.redditclone.dto.AuthenticationRequest;
import com.example.redditclone.exception.SpringRedditException;
import com.example.redditclone.model.*;
import com.example.redditclone.repository.BlackListTokenRepository;
import com.example.redditclone.repository.UserRepository;
import com.example.redditclone.repository.VerificationTokenRepository;
import com.example.redditclone.utility.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProviderService jwtProviderService;

    @Autowired
    private BlackListTokenRepository blackListTokenRepository;

    @Autowired
    private ExecutorService executorService;

    @Transactional
    public void authorise(AuthenticationRequest authenticationRequest) throws SpringRedditException {
        User user = new User();
        user.setUsername(authenticationRequest.getUsername());
        user.setPassword(encodePassword(authenticationRequest.getPassword()));
        user.setEmailId(authenticationRequest.getEmail());
        user.setEnabled(false);
        Date date = Calendar.getInstance().getTime();
        user.setCreatedTime(date);
         /*
//                If instead of  @async notation we can make a separate thread for this as it is an independent task.
                    TaskMail taskMail = new TaskMail(mailService, user.getEmailId(), message);
                taskMail.start();
         */

        sendMail(user);

        userRepository.save(user);
        log.info(" --- User Registration Successful, Sending Activaton Mail .....");


    }

    private void sendMail(User user) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String token = generateVerificationToken(user);
                    String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the" +
                            "below url to activate your account : " + AppConstants.ACTIVATION_LINK + "/" + token);
                    mailService.sendMail(
                            new NotificationEmail("Please Activate Your Account", user.getEmailId(), message));
                } catch (SpringRedditException e) {
                    log.error("Send Activation code failed.");
                }
            }
        });
    }

    private String generateVerificationToken(User user) {
        String tokenId = UUID.randomUUID().toString();
        VerificationToken token = new VerificationToken();
        token.setToken(tokenId);
        token.setUser(user);
        verificationTokenRepository.save(token);
        return tokenId;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void verifyAccount(String token) throws SpringRedditException {
        VerificationToken verifiedToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchAndEnableUser(verifiedToken);
    }

    private void fetchAndEnableUser(VerificationToken verificationToken) throws SpringRedditException {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new SpringRedditException("User not Found with id " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) throws SpringRedditException {
        Authentication authenticate = null;
        try {
            //This is the standard token which Spring MVC creates for an authentication.
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new SpringRedditException("Incorrect username or password\n" + ex);
        }
//
//        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(
//                () -> new SpringRedditException("User not found with username " + loginRequest.getUsername())
//        );
        String jwt = jwtProviderService.generateToken(authenticate);
        return new AuthenticationResponse(loginRequest.getUsername(), jwt);
    }


    @Transactional(readOnly = true)
    public User getCurrentUser() throws SpringRedditException {
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userResult = userRepository.findByUsername(userName).orElseThrow(
                () -> new SpringRedditException("No username found for : " + userName)
        );
        return userResult;
    }

    @Transactional
    public void logout(String authorizationHeader) throws SpringRedditException {
        BlacklistTokens blacklistTokens = new BlacklistTokens();
        String jwt = authorizationHeader.substring(7);
        blacklistTokens.setJwtToken(jwt);
        blackListTokenRepository.save(blacklistTokens);
    }
}

/*
class TaskMail extends Thread {

    private static final Logger log = LoggerFactory.getLogger(TaskMail.class);

    private MailService mailService;
    private String emailId;
    private String message;


    public TaskMail(MailService mailService, String emailId, String message) {
        this.mailService = mailService;
        this.emailId = emailId;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            mailService.sendMail(
                    new NotificationEmail("Please Activate Your Account", emailId, message));
        } catch (SpringRedditException e) {
            log.error(e.getMessage());
        }
    }
}
 */
