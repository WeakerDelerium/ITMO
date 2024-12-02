package com.web4back.dao;

import com.web4back.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointDAO extends JpaRepository<PointEntity, Long> {
    List<PointEntity> findByUserId(Long userId);
}
