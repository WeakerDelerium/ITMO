package server.commands;

import common.commands.Command;
import common.exception.CommandNotFoundException;
import common.exception.ScriptFormatException;
import common.exception.WrongNumberOfArgumentsException;
import common.managers.CommandManager;
import common.ui.Console;
import common.ui.UserInput;

import java.io.IOException;

public class ExitCommand extends Command {
    private final CommandManager commandManager;
    private final UserInput userInput;

    public ExitCommand(CommandManager commandManager, UserInput userInput) {
        super("exit", null, "end the program (without saving to a file)");
        this.userInput = userInput;
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException, IOException,
            InterruptedException, CommandNotFoundException, ClassNotFoundException, ScriptFormatException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.userInput.readSelect().equalsIgnoreCase("Y")) {
            this.commandManager.executeCommand("save", new String[0]);
            console.println("Exiting...");
            System.exit(0);
        } else console.println("Exit command was canceled");
    }
}
