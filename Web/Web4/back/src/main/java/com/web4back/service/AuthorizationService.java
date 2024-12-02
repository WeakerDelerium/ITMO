package com.web4back.service;

import com.web4back.dao.UserDAO;
import com.web4back.dto.CredentialsDTO;
import com.web4back.dto.TokenDTO;
import com.web4back.entity.UserEntity;
import com.web4back.exception.*;
import com.web4back.util.PasswordHasher;
import com.web4back.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final UserDAO userDAO;
    private final UserSessionService userSessionService;
    private final RandomStringGenerator randomStringGenerator;
    private final PasswordHasher passwordHasher;

    @Autowired
    public AuthorizationService(UserDAO userDAO,
                                    UserSessionService userSessionService,
                                    RandomStringGenerator randomStringGenerator,
                                    PasswordHasher passwordHasher) {
        this.userDAO = userDAO;
        this.userSessionService = userSessionService;
        this.randomStringGenerator = randomStringGenerator;
        this.passwordHasher = passwordHasher;
    }

    public CredentialsDTO registerUser(String username, String password) throws UsernameExistsException, InvalidAuthorizationDataException {
        validateAuthorizationData(username, password);

        if (userDAO.findByUsername(username).isPresent()) {
            throw new UsernameExistsException("User with username: " + username + " already exists");
        }

        String salt = randomStringGenerator.generate();
        password = passwordHasher.get_SHA_512_SecurePassword(password + salt);

        UserEntity user = UserEntity
                .builder()
                .username(username)
                .password(password)
                .salt(salt)
                .build();

        userDAO.save(user);

        return userSessionService.startSession(user);
    }

    public CredentialsDTO authenticateUser(String username, String password) throws InvalidAuthorizationDataException, UserNotFoundException, AuthenticationException {
        validateAuthorizationData(username, password);

        UserEntity user = userDAO
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " does not exists"));

        password = passwordHasher.get_SHA_512_SecurePassword(password + user.getSalt());

        if (password.equals(user.getPassword())) {
            return userSessionService.startSession(user);
        }

        throw new AuthenticationException("Wrong password");
    }

    public void logout(String token) {
        userSessionService.endSession(token);
    }

    public TokenDTO getRefreshedToken(TokenDTO token) throws SessionTimeoutException, SessionNotFoundException {
        return userSessionService.refreshToken(token.getToken());
    }

    private void validateAuthorizationData(String username, String password) throws InvalidAuthorizationDataException {
        if (username == null)
            throw new InvalidAuthorizationDataException("Username is null");
        if (password == null)
            throw new InvalidAuthorizationDataException("Password is null");
        if (username.isEmpty())
            throw new InvalidAuthorizationDataException("Username is empty");
        if (password.isEmpty())
            throw new InvalidAuthorizationDataException("Password is empty");
        if (username.length() > 50)
            throw new InvalidAuthorizationDataException("Username max length is 50");
    }
}
