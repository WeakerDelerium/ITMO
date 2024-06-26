package server.exec;

public class WrongNumberOfArgumentsException extends Exception {
    public WrongNumberOfArgumentsException(Integer expected, Integer found) {
        super(String.format("Wrong number of arguments (Expected: %d, Found: %d). Try `help`", expected, found));
    }
}
