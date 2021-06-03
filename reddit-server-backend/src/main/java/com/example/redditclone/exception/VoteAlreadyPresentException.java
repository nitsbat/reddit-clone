package com.example.redditclone.exception;

public class VoteAlreadyPresentException extends SpringRedditException {

    public VoteAlreadyPresentException(String message) {
        super(message);
    }
}
