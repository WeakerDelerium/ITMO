package client.commands;

import common.commands.Command;
import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;
import common.ui.UserInput;

public class ExitCommand extends Command {
    private final UserInput userInput;

    public ExitCommand(UserInput userInput) {
        super("exit", null, "end the program (without saving to a file)");
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.userInput.readSelect().equalsIgnoreCase("Y")) {
            console.println("Exiting...");
            System.exit(0);
        } else console.println("Exit command was canceled");
    }
}
