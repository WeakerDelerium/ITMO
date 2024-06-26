package common.commands;

import common.managers.CommandManager;
import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;

public class HistoryCommand extends Command {
    private final CommandManager commandManager;

    public HistoryCommand(CommandManager commandManager) {
        super("history", null, "print the last 14 commands (without their arguments)");
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.commandManager.getCommandHistory().isEmpty()) console.println("Command history is empty");
        else this.commandManager.getCommandHistory().forEach(console::println);
    }
}
