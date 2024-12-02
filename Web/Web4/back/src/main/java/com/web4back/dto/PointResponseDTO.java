package com.web4back.dto;

import com.web4back.entity.PointEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointResponseDTO implements Serializable {
    private double x;
    private double y;
    private double r;
    private boolean hit;
    private long exec;

    public PointResponseDTO(@NotNull PointEntity pointEntity) {
        this.x = pointEntity.getX();
        this.y = pointEntity.getY();
        this.r = pointEntity.getR();
        this.hit = pointEntity.isHit();
        this.exec = pointEntity.getExec();
    }
}
