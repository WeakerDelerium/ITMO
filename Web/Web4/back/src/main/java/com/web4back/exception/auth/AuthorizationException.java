package ru.web4back.exception.auth;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorizationException extends Exception {

    private final HttpStatus status;

    public AuthorizationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
