package com.web4back.dao;

import com.web4back.entity.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserSessionDAO extends JpaRepository<UserSessionEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE UserSessionEntity u SET u.accessToken = :#{session.accessToken} WHERE u.id = :#{session.id}")
    void updateSession(UserSessionEntity session);

    void deleteByAccessToken(String token);
    void deleteByRefreshToken(String token);

    Optional<UserSessionEntity> findByAccessToken(String token);
    Optional<UserSessionEntity> findByRefreshToken(String token);
}
