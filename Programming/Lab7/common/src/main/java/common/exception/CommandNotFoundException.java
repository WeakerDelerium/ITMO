package common.exception;

public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String message) {
        super(String.format("command `%s` not found, try `help`", message));
    }
}
