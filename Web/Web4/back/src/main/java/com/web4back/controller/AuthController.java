package ru.web4back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.web4back.dto.auth.AuthResponseDTO;
import ru.web4back.dto.auth.LoginPayloadDTO;
import ru.web4back.dto.auth.RegisterPayloadDTO;
import ru.web4back.dto.token.AccessTokenDTO;
import ru.web4back.dto.token.RefreshTokenDTO;
import ru.web4back.exception.auth.AuthorizationException;
import ru.web4back.exception.token.RefreshTokenException;
import ru.web4back.service.AuthService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Validated @RequestBody LoginPayloadDTO payload) throws AuthorizationException {
        AuthResponseDTO authResponseDTO = authService.login(payload);
        log.info("User `{}` successfully authenticated", payload.getUsername());
        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Validated @RequestBody RegisterPayloadDTO payload) throws AuthorizationException {
        AuthResponseDTO authResponseDTO = authService.register(payload);
        log.info("User `{}` successfully registered", payload.getUsername());
        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Validated @RequestBody RefreshTokenDTO refresh) {
        boolean success = authService.logout(refresh);
        if (success) log.info("Logout successful by refresh token: {}", refresh.getToken());
        else log.warn("Logout successful, but user record remained due to invalid refresh token: {}", refresh.getToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tokens")
    public ResponseEntity<AccessTokenDTO> updateTokens(@Validated @RequestBody RefreshTokenDTO refresh) throws RefreshTokenException {
        AccessTokenDTO accessTokenDTO = authService.updateTokens(refresh);
        log.info("User with refresh token `{}` successfully updated access token on `{}`", refresh.getToken(), accessTokenDTO.getToken());
        return ResponseEntity.ok(accessTokenDTO);
    }

}
