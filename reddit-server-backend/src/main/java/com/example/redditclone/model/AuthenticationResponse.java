package com.example.redditclone.model;

public class AuthenticationResponse {

    private String username;
    private String jwt;

    public AuthenticationResponse(String username, String jwt) {
        this.username = username;
        this.jwt = jwt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
