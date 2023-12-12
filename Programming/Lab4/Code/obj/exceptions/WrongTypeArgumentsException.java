package obj.exceptions;

public class WrongTypeArgumentsException extends Exception {
    private final static String defaultMessage = "Несочетаемые с действием данные";

    public WrongTypeArgumentsException() {
        super(defaultMessage);
    }
    public WrongTypeArgumentsException(String message) {
        super(message);
    }
}
