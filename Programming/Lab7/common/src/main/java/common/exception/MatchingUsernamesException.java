package common.exception;

public class MatchingUsernamesException extends UserAuthorizationException {
    public MatchingUsernamesException(String username) {
        super(String.format("user `%s` exists", username));
    }
}
