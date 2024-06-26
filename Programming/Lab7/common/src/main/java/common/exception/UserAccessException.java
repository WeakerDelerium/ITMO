package common.exception;

public class UserAccessException extends IllegalArgumentException {
    public UserAccessException() {
        super("access denied - this is not your item");
    }
}
