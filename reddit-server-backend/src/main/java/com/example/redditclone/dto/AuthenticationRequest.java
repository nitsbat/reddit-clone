package com.example.redditclone.dto;

public class AuthenticationRequest {
    private String userName;
    private String password;
    private String emailI;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailI() {
        return emailI;
    }

    public void setEmailI(String emailI) {
        this.emailI = emailI;
    }
}
