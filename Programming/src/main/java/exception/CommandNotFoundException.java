package exception;

public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String message) {
        super(String.format("Command `%s` not found, try `help`", message));
    }
}
