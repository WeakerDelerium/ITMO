package com.web4back.util;

import com.web4back.dto.PointRequestDTO;
import com.web4back.dto.PointResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AreaChecker {
    public PointResponseDTO checkPoint(PointRequestDTO request) {
        PointResponseDTO response = PointResponseDTO
                .builder()
                .x(request.getX())
                .y(request.getY())
                .r(request.getR())
                .build();

        long startTime = System.nanoTime() / 1000;
        response.setHit(isHit(request.getX(), request.getY(), request.getR()));
        long endTime = System.nanoTime() / 1000;
        response.setExec(endTime - startTime);
        return response;
    }

    private boolean isHit(double x, double y, double r) {
        return (x * x + y * y <= r * r && y >= 0 && x >= 0) ||
                (0 < x && x <= r && -r <= y && y < 0) ||
                (y + x >= -r && x <= 0 && y <= 0);
    }
}