package common.commands;

import common.exception.CommandNotFoundException;
import common.exception.ScriptFormatException;
import common.exception.WrongNumberOfArgumentsException;

import java.io.IOException;
import java.sql.SQLException;

public interface ICommand {
    void execute(String[] args) throws WrongNumberOfArgumentsException,
            IOException, InterruptedException, CommandNotFoundException, ScriptFormatException, ClassNotFoundException, SQLException;
    String getName();
    String[] getArgs();
    String getDescription();
}
