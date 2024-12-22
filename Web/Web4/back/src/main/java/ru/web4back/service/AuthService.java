package ru.web4back.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.web4back.dto.auth.AuthResponseDTO;
import ru.web4back.dto.auth.LoginPayloadDTO;
import ru.web4back.dto.auth.RegisterPayloadDTO;
import ru.web4back.dto.token.AccessTokenDTO;
import ru.web4back.dto.token.RefreshTokenDTO;
import ru.web4back.entity.RefreshToken;
import ru.web4back.entity.Role;
import ru.web4back.entity.User;
import ru.web4back.exception.auth.AuthenticationException;
import ru.web4back.exception.auth.AuthorizationException;
import ru.web4back.exception.auth.UserAlreadyExistsException;
import ru.web4back.exception.auth.UserNotFoundException;
import ru.web4back.exception.token.RefreshTokenException;
import ru.web4back.exception.token.RefreshTokenExpiredException;
import ru.web4back.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    private AuthResponseDTO buildResponse(User user) {
        return AuthResponseDTO
                .builder()
                .access(accessTokenService.generate(user))
                .refresh(refreshTokenService.generate(user))
                .build();
    }

    public AuthResponseDTO login(LoginPayloadDTO payload) throws AuthorizationException {
        String username = payload.getUsername();
        String password = payload.getPassword();

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User `%s` not found".formatted(username)));

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Wrong password for user `%s`".formatted(username));
        }

        return buildResponse(user);
    }

    public AuthResponseDTO register(RegisterPayloadDTO payload) throws AuthorizationException {
        String username = payload.getUsername();
        String password = payload.getPassword();

        if (userRepository.findByUsername(username).isPresent())
            throw new UserAlreadyExistsException("User `%s` already exist".formatted(username));

        User user = User
                .builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return buildResponse(user);
    }

    public boolean logout(RefreshTokenDTO refresh) {
        return refreshTokenService.delete(refresh.getToken()) != 0;
    }

    public AccessTokenDTO updateTokens(RefreshTokenDTO refresh) throws RefreshTokenException {
        UUID token = refresh.getToken();

        RefreshToken refreshToken = refreshTokenService.get(token);

        if (refreshTokenService.isExpired(refreshToken)) {
            refreshTokenService.delete(token);
            throw new RefreshTokenExpiredException();
        }

        String accessToken = accessTokenService.generate(refreshToken.getUser());

        return AccessTokenDTO
                .builder()
                .token(accessToken)
                .build();
    }

}
