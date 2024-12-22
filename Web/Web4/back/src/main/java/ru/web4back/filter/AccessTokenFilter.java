package ru.web4back.filter;

import com.auth0.jwt.exceptions.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ru.web4back.service.AccessTokenService;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessTokenFilter extends OncePerRequestFilter {

    private static final RequestMatcher ignore = new OrRequestMatcher(
            new AntPathRequestMatcher("/auth/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/auth/**", HttpMethod.POST.name())
    );

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer";

    private final AccessTokenService accessTokenService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
                                    @NonNull HttpServletResponse resp,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        String authHeader = req.getHeader(AUTHORIZATION_HEADER);
        log.info("Received JWT header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            log.warn("Bearer token not found");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String token = authHeader.substring(BEARER_PREFIX.length()).trim();
        String username;

        try {
            username = accessTokenService.getUsername(token);
        } catch (JWTVerificationException e) {
            log.error("Data extraction from JWT token failed: {}", accessTokenService.getCauseMessage(e));
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        log.info("Bearer token successfully handled");
        chain.doFilter(req, resp);
    }

    @Override
    public boolean shouldNotFilter(@NonNull HttpServletRequest req) {
        return ignore.matches(req);
    }

}
