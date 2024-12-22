package ru.web4back.exception.token;

public class RefreshTokenNotFoundException extends RefreshTokenException {
    public RefreshTokenNotFoundException() {
        super("Refresh token not found");
    }
}
