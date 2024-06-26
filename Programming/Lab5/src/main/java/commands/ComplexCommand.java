package commands;

import exception.WrongNumberOfArgumentsException;

import java.util.HashMap;

public abstract class ComplexCommand extends Command {
    public ComplexCommand(String name, String[] args, String description) {
        super(name, args, description);
    }

    public abstract void scriptExecute(String[] args, HashMap<String, String> routeScriptArgs) throws WrongNumberOfArgumentsException;
}
