package client.commands;

import common.exception.WrongNumberOfArgumentsException;
import common.ui.RouteReader;

import java.io.IOException;

public abstract class ComplexCommand extends UserCommand {
    public ComplexCommand(String name, String[] args, String description) {
        super(name, args, description);
    }

    public abstract void scriptExecute(String[] args, RouteReader routeScriptArgs)
            throws WrongNumberOfArgumentsException, IOException, ClassNotFoundException;
}
