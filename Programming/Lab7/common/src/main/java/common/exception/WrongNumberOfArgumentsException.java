package common.exception;

public class WrongNumberOfArgumentsException extends Exception {
    public WrongNumberOfArgumentsException(Integer expected, Integer found) {
        super(String.format("wrong number of arguments (Expected: %d, Found: %d). Try `help`", expected, found));
    }
}
