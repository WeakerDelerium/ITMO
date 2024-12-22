package ru.web4back.exception.auth;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends AuthorizationException {

    private static final HttpStatus STATUS = HttpStatus.CONFLICT;

    public UserAlreadyExistsException(String message) {
        super(message, STATUS);
    }

}
