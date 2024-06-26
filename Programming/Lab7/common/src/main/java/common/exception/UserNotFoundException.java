package common.exception;

public class UserNotFoundException extends UserAuthorizationException {
    public UserNotFoundException(String username) {
        super(String.format("user `%s` doesn't exist", username));
    }
}
