package ru.web4back.exception;

public class JWTSecretNotFoundException extends RuntimeException {
    public JWTSecretNotFoundException() {
        super("Property token.access.secret is required");
    }
}