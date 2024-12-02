package com.web4back.filter;

import com.web4back.exception.SessionNotFoundException;
import com.web4back.exception.SessionTimeoutException;
import com.web4back.service.UserSessionService;
import com.web4back.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserSessionService userSessionService;

    @Autowired
    public AuthorizationFilter(UserSessionService userSessionService, JWTUtil jwtUtil) {
        this.userSessionService = userSessionService;
        this.jwtUtil = jwtUtil;
    }

    private static final Set<String> SKIP_PATHS = new HashSet<>(Arrays.asList(
            "/auth/signup",
            "/auth/login",
            "/auth/refresh"
    ));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        log.info("Received request with path: {}", path);

        if (SKIP_PATHS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("Authorization header not found");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authorization header must be provided");
            return;
        }

        String token = authHeader.substring("Bearer".length()).trim();
        log.info("Received token: {}", token);

        try {
            userSessionService.validateToken(token);
        } catch (SessionNotFoundException e) {
            log.info("Session not found for token: {}", token);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
            return;
        } catch (SessionTimeoutException e) {
            log.info("Session timeout for token: {}", token);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Login timeout expired");
            return;
        }

        Long userId = jwtUtil.getUserId(token);

        request.setAttribute("userId", userId);
        request.setAttribute("token", token);

        filterChain.doFilter(request, response);
    }
}
