package common.commands;

import common.managers.CommandManager;
import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;

public class HelpCommand extends Command {
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        super("help", null, "display help on available commands");
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        this.commandManager.getCommandList().values().forEach(Console.getInstance()::println);
    }
}
