package ru.web4back.exception.token;

public class RefreshTokenExpiredException extends RefreshTokenException {
    public RefreshTokenExpiredException() {
        super("Refresh token expired");
    }
}
