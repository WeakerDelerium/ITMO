package server.exec;

public class CommandNotSupportedException extends IllegalArgumentException {
    public CommandNotSupportedException() {
        super("Command not supported");
    }
}
