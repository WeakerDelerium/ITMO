package server.commands;

import common.commands.Command;
import common.exception.CommandNotFoundException;
import common.exception.ScriptFormatException;
import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;
import common.ui.UserInput;
import server.DB.DBAttach;

import java.io.IOException;
import java.sql.SQLException;

public class ExitCommand extends Command {
    private final UserInput userInput;

    public ExitCommand(UserInput userInput) {
        super("exit", null, "end the program (without saving to a file)");
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException, IOException,
            InterruptedException, CommandNotFoundException, ClassNotFoundException, ScriptFormatException, SQLException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.userInput.readExitSelect().equalsIgnoreCase("Y")) {
            console.println("Exiting...");
            DBAttach.getInstance().disconnect();
            System.exit(0);
        } else console.println("Exit command was canceled");
    }
}
