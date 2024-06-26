package common.exception;

public class WrongPasswordException extends UserAuthorizationException {
    public WrongPasswordException() {
        super("wrong password");
    }
}
