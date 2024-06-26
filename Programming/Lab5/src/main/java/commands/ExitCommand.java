package commands;

import exception.WrongNumberOfArgumentsException;
import managers.Console;
import managers.UserInputManager;

public class ExitCommand extends Command {
    private final UserInputManager userInputManager;

    public ExitCommand(UserInputManager userInputManager) {
        super("exit", null, "end the program (without saving to a file)");
        this.userInputManager = userInputManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        if (this.userInputManager.readSelect().equalsIgnoreCase("YES")) {
            Console.println("Exiting...");
            System.exit(0);
        } else Console.println("Exit command was canceled");
    }
}
