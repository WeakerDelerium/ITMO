package server.commands;

import common.commands.Command;
import common.managers.CommandManager;
import server.ui.Console;

import server.exec.WrongNumberOfArgumentsException;

public class HelpCommand extends Command {
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        super("help", null, "display help on available commands");
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        this.commandManager.getCommandList().values().forEach(command -> {
            String message = command.toString();
            if (command instanceof ServerCommand) message += " [NOT SUPPORTED HERE]";
            Console.getInstance().println(message);
        });
    }
}
