package ru.web4back.exception.auth;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends AuthorizationException {

    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    public AuthenticationException(String message) {
        super(message, STATUS);
    }

}
