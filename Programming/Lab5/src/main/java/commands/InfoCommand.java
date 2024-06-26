package commands;

import exception.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.Console;

public class InfoCommand extends Command {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", null, "print information about the collection to the standard output stream.");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console.println(this.collectionManager.getCollectionInfo());
    }
}
