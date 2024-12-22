package ru.web4back.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.exceptions.*;

import org.springframework.stereotype.Service;

import ru.web4back.config.TokenConfig;
import ru.web4back.entity.User;

import java.time.Instant;

@Service
public class AccessTokenService {

    private final Algorithm SECRET_KEY;
    private final Long ACCESS_TOKEN_VALIDITY_SECONDS;

    public AccessTokenService(TokenConfig tokenConfig) {
        SECRET_KEY = Algorithm.HMAC256(tokenConfig.access().secret());
        ACCESS_TOKEN_VALIDITY_SECONDS = tokenConfig.access().validity().toSeconds();
    }

    public String generate(User user) {
        return JWT
                .create()
                .withClaim("username", user.getUsername())
                .withExpiresAt(Instant.now().plusSeconds(ACCESS_TOKEN_VALIDITY_SECONDS))
                .sign(SECRET_KEY);
    }

    public String getUsername(String token) {
        return JWT
                .require(SECRET_KEY)
                .build()
                .verify(token)
                .getClaim("username")
                .asString();
    }

    public String getCauseMessage(JWTVerificationException e) {
        return switch (e.getClass().getSimpleName()) {
            case "JWTDecodeException" -> "Cannot decode JWT token";
            case "SignatureVerificationException", "AlgorithmMismatchException" -> "Signature verification wasn't successful";
            case "TokenExpiredException" -> "Token expired";
            case "InvalidClaimException" -> "Token claims mismatch";
            default -> "Unexpected error";
        };
    }

}
