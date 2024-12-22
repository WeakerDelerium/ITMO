package ru.web4back.exception.auth;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AuthorizationException {

    private final static HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public UserNotFoundException(String message) {
        super(message, STATUS);
    }

}
