package ru.web4back.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.web4back.config.TokenConfig;
import ru.web4back.entity.RefreshToken;
import ru.web4back.entity.User;
import ru.web4back.exception.token.RefreshTokenException;
import ru.web4back.exception.token.RefreshTokenNotFoundException;
import ru.web4back.repository.TokenRepository;
import ru.web4back.utils.Converter;

import java.util.UUID;

@Service
public class RefreshTokenService {

    private final Long REFRESH_TOKEN_VALIDITY_SECONDS;

    private final TokenRepository tokenRepository;

    public RefreshTokenService(TokenConfig tokenConfig, TokenRepository tokenRepository) {
        REFRESH_TOKEN_VALIDITY_SECONDS = tokenConfig.refresh().validity().toSeconds();
        System.out.println(REFRESH_TOKEN_VALIDITY_SECONDS);
        this.tokenRepository = tokenRepository;
    }

    public UUID generate(User user) {
        return tokenRepository
                .save(RefreshToken
                        .builder()
                        .user(user)
                        .createAt(Converter.secTimeShift.apply(0L))
                        .expiresAt(Converter.secTimeShift.apply(REFRESH_TOKEN_VALIDITY_SECONDS))
                        .build())
                .getId();
    }

    public RefreshToken get(UUID id) throws RefreshTokenException {
        return tokenRepository
                .findById(id)
                .orElseThrow(RefreshTokenNotFoundException::new);
    }

    @Transactional
    public int delete(UUID id) {
        return tokenRepository.deleteAndCountById(id);
    }

    public boolean isExpired(RefreshToken refreshToken) {
        return refreshToken
                .getExpiresAt()
                .before(Converter.secTimeShift.apply(0L));
    }

}
