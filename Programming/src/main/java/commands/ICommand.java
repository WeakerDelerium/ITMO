package commands;

import exception.CommandNotFoundException;
import exception.ScriptFormatException;
import exception.WrongNumberOfArgumentsException;

import java.io.IOException;

public interface ICommand {
    void execute(String[] args) throws WrongNumberOfArgumentsException,
            IOException, InterruptedException, CommandNotFoundException, ScriptFormatException;
    String getName();
    String[] getArgs();
    String getDescription();
}
