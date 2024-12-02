package com.web4back.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JWTUtil {

    private SecretKey key;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @PostConstruct
    private void init() {
        if (secretKey == null || secretKey.isEmpty()) {
            log.error("SecretKey property was not found in application properties");
            return;
        }
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
        log.info("JWT secret key was loaded successfully");
    }

    public String generateToken(Long userId, Instant expiresAt) {
        return Jwts
                .builder()
                .claim("userId", userId)
                .expiration(Date.from(expiresAt))
                .signWith(key)
                .compact();
    }

    public Long getUserId(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("userId", Long.class);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isExpired(String token) {
        try {
            Date expirationTime = Jwts
                    .parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            return expirationTime == null || expirationTime.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

}
