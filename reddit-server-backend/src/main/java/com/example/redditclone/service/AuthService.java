package com.example.redditclone.service;

import com.example.redditclone.dto.AuthenticationRequest;
import com.example.redditclone.model.User;
import com.example.redditclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void authorise(AuthenticationRequest authenticationRequest) {
        User user = new User();
        user.setUsername(authenticationRequest.getUserName());
        user.setPassword(encodePassword(authenticationRequest.getPassword()));
        user.setEmailId(authenticationRequest.getEmailI());
        user.setEnabled(false);
        Date date = Calendar.getInstance().getTime();
        user.setCreatedTime(date);
        userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
