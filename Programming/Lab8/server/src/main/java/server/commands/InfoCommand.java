package server.commands;

import common.commands.Command;
import server.ui.Console;

import server.managers.CollectionManager;
import server.exec.WrongNumberOfArgumentsException;

public class InfoCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public InfoCommand() {
        super("info", null, "print information about the collection to the standard output stream.");
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console.getInstance().println(this.collectionManager.getCollectionInfo());
    }
}
