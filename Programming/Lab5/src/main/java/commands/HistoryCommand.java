package commands;

import exception.WrongNumberOfArgumentsException;
import managers.CommandManager;
import managers.Console;

public class HistoryCommand extends Command {
    private final CommandManager commandManager;

    public HistoryCommand(CommandManager commandManager) {
        super("history", null, "print the last 14 commands (without their arguments)");
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        if (this.commandManager.getCommandHistory().isEmpty()) Console.println("Command history is empty");
        else this.commandManager.getCommandHistory().forEach(Console::println);
    }
}
