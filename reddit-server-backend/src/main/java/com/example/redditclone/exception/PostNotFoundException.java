package com.example.redditclone.exception;

public class PostNotFoundException extends SpringRedditException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
