package ru.web4back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.web4back.dto.point.PointRequestDTO;
import ru.web4back.dto.point.PointResponseDTO;
import ru.web4back.entity.User;
import ru.web4back.service.PointService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/all")
    public ResponseEntity<List<PointResponseDTO>> getAll(@AuthenticationPrincipal User user) {
        List<PointResponseDTO> points = pointService.getAll(user);
        log.info("User `{}` successfully loaded point list", user.getUsername());
        return ResponseEntity.ok(points);
    }

    @PostMapping("/check")
    public ResponseEntity<PointResponseDTO> check(@AuthenticationPrincipal User user, @Validated @RequestBody PointRequestDTO request) {
        PointResponseDTO response = pointService.check(user, request);
        log.info("User `{}` successfully added new point", user.getUsername());
        return ResponseEntity.ok(response);
    }

}
