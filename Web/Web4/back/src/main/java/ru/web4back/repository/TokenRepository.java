package ru.web4back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import ru.web4back.entity.RefreshToken;

import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken, UUID> {
    @Modifying
    @Query("DELETE RefreshToken token WHERE token.id = :id")
    int deleteAndCountById(@Param("id") UUID id);
}
