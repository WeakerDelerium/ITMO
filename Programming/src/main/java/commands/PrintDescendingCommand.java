package commands;

import exception.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.Console;

public class PrintDescendingCommand extends Command {
    private final CollectionManager collectionManager;

    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending", null, "display collection elements in descending order");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        if (this.collectionManager.getRouteCollection().isEmpty()) Console.println("Collection is empty");
        else this.collectionManager.descendingGroupByDistance().values().forEach(routes -> routes.forEach(Console::println));
    }
}
