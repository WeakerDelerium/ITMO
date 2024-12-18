package com.web4back.service;

import com.web4back.dao.UserSessionDAO;
import com.web4back.dto.CredentialsDTO;
import com.web4back.dto.TokenDTO;
import com.web4back.entity.UserEntity;
import com.web4back.entity.UserSessionEntity;
import com.web4back.exception.SessionNotFoundException;
import com.web4back.exception.SessionTimeoutException;
import com.web4back.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class UserSessionService  {

    private final UserSessionDAO userSessionDAO;
    private final JWTUtil jwtUtil;

    @Autowired
    public UserSessionService(UserSessionDAO userSessionDAO,
                              JWTUtil jwtUtil) {
        this.userSessionDAO = userSessionDAO;
        this.jwtUtil = jwtUtil;
    }

    private final long ACCESS_TOKEN_VALIDITY_MINUTES = 30;
    private final long REFRESH_TOKEN_VALIDITY_HOURS = 5;

    public CredentialsDTO startSession(UserEntity user) {
        String accessToken = jwtUtil.generateToken(user.getId(), Instant.now().plus(ACCESS_TOKEN_VALIDITY_MINUTES, ChronoUnit.MINUTES));
        String refreshToken = jwtUtil.generateToken(user.getId(), Instant.now().plus(REFRESH_TOKEN_VALIDITY_HOURS, ChronoUnit.HOURS));

        UserSessionEntity userSessionEntity = UserSessionEntity
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        userSessionDAO.save(userSessionEntity);

        return new CredentialsDTO(accessToken, refreshToken);
    }

    public void endSession(String token) {
        userSessionDAO.deleteByAccessToken(token);
    }

    public void validateToken(String token) throws SessionNotFoundException, SessionTimeoutException {
        if (jwtUtil.isExpired(token)) {
            throw new SessionTimeoutException("Session with token " + token + " is expired");
        }
        if (userSessionDAO.findByAccessToken(token).isEmpty()) {
            throw new SessionNotFoundException("Session with token " + token + " does not exist");
        }
    }

    public TokenDTO refreshToken(String token) throws SessionNotFoundException, SessionTimeoutException {
        if (jwtUtil.isExpired(token)) {
            userSessionDAO.deleteByRefreshToken(token);
            throw new SessionTimeoutException("Session with token " + token + " is expired");
        }

        UserSessionEntity userSession = userSessionDAO
                .findByRefreshToken(token)
                .orElseThrow(() -> new SessionNotFoundException("Session with token " + token + " does not exist"));

        String newAccessToken = jwtUtil.generateToken(jwtUtil.getUserId(token), Instant.now().plus(ACCESS_TOKEN_VALIDITY_MINUTES, ChronoUnit.MINUTES));
        userSession.setAccessToken(newAccessToken);
        userSessionDAO.updateSession(userSession);

        return new TokenDTO(newAccessToken);
    }
}
