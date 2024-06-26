package client.commands;

import common.exception.WrongNumberOfArgumentsException;
import common.ui.RouteBuilder;

import java.io.IOException;

public abstract class ComplexCommand extends UserCommand {
    public ComplexCommand(String name, String[] args, String description) {
        super(name, args, description);
    }

    public abstract void scriptExecute(String[] args, RouteBuilder routeScriptArgs)
            throws WrongNumberOfArgumentsException, IOException, ClassNotFoundException;
}
