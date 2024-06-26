package server.commands;

import common.commands.Command;
import server.ui.Console;

import server.managers.CollectionManager;
import server.exec.WrongNumberOfArgumentsException;

public class PrintFieldDescendingDistanceCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public PrintFieldDescendingDistanceCommand() {
        super("print_field_descending_distance", null, "display the values of the distance field of all elements in descending order");
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.collectionManager.checkEmptyCollection()) console.println("Collection is empty");
        else this.collectionManager.descendingCollection().forEach(route -> console.println(route.distance()));
    }
}
