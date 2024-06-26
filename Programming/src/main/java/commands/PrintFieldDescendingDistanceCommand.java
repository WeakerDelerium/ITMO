package commands;

import exception.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.Console;

public class PrintFieldDescendingDistanceCommand extends Command {
    private final CollectionManager collectionManager;

    public PrintFieldDescendingDistanceCommand(CollectionManager collectionManager) {
        super("print_field_descending_distance", null, "display the values of the distance field of all elements in descending order");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        if (this.collectionManager.getRouteCollection().isEmpty()) Console.println("Collection is empty");
        else this.collectionManager.descendingGroupByDistance().keySet().forEach(Console::println);
    }
}
