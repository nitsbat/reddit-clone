package com.example.redditclone.controller;

import com.example.redditclone.dto.AuthenticationRequest;
import com.example.redditclone.exception.SpringRedditException;
import com.example.redditclone.model.AuthenticationResponse;
import com.example.redditclone.model.LoginRequest;
import com.example.redditclone.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity signup(@RequestBody AuthenticationRequest request) throws SpringRedditException {
        authService.authorise(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "accountVerification/{token}")
    public ResponseEntity verifyAccount(@PathVariable String token) throws SpringRedditException {
        authService.verifyAccount(token);
        return new ResponseEntity("Account successfully activated", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody LoginRequest loginRequest)
            throws SpringRedditException {
        AuthenticationResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
