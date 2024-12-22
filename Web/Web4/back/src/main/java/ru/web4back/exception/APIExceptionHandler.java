package ru.web4back.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ru.web4back.exception.auth.AuthorizationException;
import ru.web4back.exception.token.RefreshTokenException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class APIExceptionHandler {

    private ResponseEntity<?> HTTP(HttpStatus status, Object body) {
        return ResponseEntity.status(status).body(body);
    }

    private ResponseEntity<?> error(HttpStatus status, Throwable ex) {
        return HTTP(status, ex.getMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    private ResponseEntity<?> handleAuthorizationException(AuthorizationException e) {
        log.warn("Authorization failed: {}", e.getMessage());
        return error(e.getStatus(), e);
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<?> handleRefreshTokenException(RefreshTokenException e) {
        log.warn("Token update failed: {}", e.getMessage());
        return error(HttpStatus.FORBIDDEN, e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("Invalid token format: {}", e.getMessage());
        return error(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error ->
                errors.put(error.getObjectName(), error.getDefaultMessage()));

        log.warn("Validation errors caught: {}", errors.size());
        errors.forEach((key, value) -> log.warn("{}: {}", key, value));

        return HTTP(HttpStatus.BAD_REQUEST, Map.of("ERROR", errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

}
