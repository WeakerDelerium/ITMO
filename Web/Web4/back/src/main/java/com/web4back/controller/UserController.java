package com.web4back.controller;

import com.web4back.dto.PointRequestDTO;
import com.web4back.dto.PointResponseDTO;
import com.web4back.exception.UserNotFoundException;
import com.web4back.service.UserService;
import com.web4back.util.UserPrincipals;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/main")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/points")
    public ResponseEntity<List<PointResponseDTO>> getPoints(@AuthenticationPrincipal UserPrincipals userPrincipals) {
        List<PointResponseDTO> points = userService.getAllPoints(userPrincipals.getUserId());
        return ResponseEntity.ok(points);
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkpoint(@Valid @RequestBody PointRequestDTO point, @AuthenticationPrincipal UserPrincipals userPrincipals) {
        try {
            PointResponseDTO response = userService.addPoint(point, userPrincipals.getUserId());
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
