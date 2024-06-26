package client.exec;

public class ScriptFormatException extends IllegalArgumentException {
    public ScriptFormatException() {
        super("invalidScript");
    }
}
