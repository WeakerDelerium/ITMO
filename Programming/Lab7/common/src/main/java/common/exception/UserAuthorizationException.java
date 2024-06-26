package common.exception;

public class UserAuthorizationException extends IllegalArgumentException {
    public UserAuthorizationException() {
        super("error while checking user authorization data");
    }

    public UserAuthorizationException(String message) {
        super(message);
    }
}
