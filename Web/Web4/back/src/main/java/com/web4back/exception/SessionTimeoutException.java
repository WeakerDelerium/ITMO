package com.web4back.exception;

public class SessionTimeoutException extends Exception {
    public SessionTimeoutException(String message) {
        super(message);
    }
}