package com.web4back.dao;

import com.web4back.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
