package com.web4back.exception;

public class UsernameExistsException extends Exception {
    public UsernameExistsException(String message) {
        super(message);
    }
}