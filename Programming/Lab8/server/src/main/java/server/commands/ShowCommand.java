package server.commands;

import common.commands.Command;
import server.ui.Console;

import server.exec.WrongNumberOfArgumentsException;

import server.managers.CollectionManager;

public class ShowCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public ShowCommand() {
        super("show", null, "print to standard output all the elements of the collection in string representation");
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        console.println("id\t\tname\t\tcoord x\t\tcoord y\t\tcreation_date\t\tfrom x\t\tfrom y\t\tfrom z\t\tfrom name\t\tto x\t\tto y\t\tto name\t\tdistance");
        console.println(collectionManager.getCollectionString());
    }
}
