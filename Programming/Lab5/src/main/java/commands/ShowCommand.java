package commands;

import collection.Route;
import exception.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.Console;

import java.util.TreeSet;

public class ShowCommand extends Command {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", null, "print to standard output all the elements of the collection in string representation");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        if (this.collectionManager.getRouteCollection().isEmpty()) Console.println("Collection is empty");
        else this.collectionManager.getRouteCollection().forEach(Console::println);
    }
}
