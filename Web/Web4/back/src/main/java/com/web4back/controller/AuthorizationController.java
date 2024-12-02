package com.web4back.controller;

import com.web4back.dto.CredentialsDTO;
import com.web4back.dto.TokenDTO;
import com.web4back.dto.UserDTO;
import com.web4back.exception.*;
import com.web4back.service.AuthorizationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDTO user) {
        try {
            CredentialsDTO token = authorizationService.registerUser(user.getLogin(), user.getPassword());
            log.info("Successfully registered user: {}", user.getLogin());
            return ResponseEntity.ok(token);
        } catch (UsernameExistsException | InvalidAuthorizationDataException e) {
            log.info("Registration failed for user {}: {}", user.getLogin(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@Valid @RequestBody UserDTO user) {
        try {
            CredentialsDTO token = authorizationService.authenticateUser(user.getLogin(), user.getPassword());
            log.info("Successfully authenticated user: {}", user.getLogin());
            return ResponseEntity.ok(token);
        } catch (InvalidAuthorizationDataException e) {
            log.info("Authentication failed for user {}: {}", user.getLogin(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (UserNotFoundException e) {
            log.info("Authentication failed for user {}: {}", user.getLogin(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AuthenticationException e) {
            log.info("Authentication failed for user {}: {}", user.getLogin(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String token = (String) authentication.getCredentials();
            authorizationService.logout(token);
            log.info("Successfully logged out user with id: {}", authentication.getName());
            return ResponseEntity.ok("Logout successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active session found");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenDTO token) {
        try {
            TokenDTO newToken = authorizationService.getRefreshedToken(token);
            log.debug("Successfully refreshed token: {} -> {}", token.getToken(), newToken.getToken());
            return ResponseEntity.ok(newToken);
        } catch (SessionTimeoutException | SessionNotFoundException e) {
            log.info("Unable to refresh token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
