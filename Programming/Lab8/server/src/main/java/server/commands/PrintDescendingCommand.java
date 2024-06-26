package server.commands;

import common.commands.Command;
import server.ui.Console;

import server.managers.CollectionManager;
import server.exec.WrongNumberOfArgumentsException;

public class PrintDescendingCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public PrintDescendingCommand() {
        super("print_descending", null, "display collection elements in descending order");
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.collectionManager.checkEmptyCollection()) console.println("Collection is empty");
        else this.collectionManager.descendingCollection().forEach(console::println);
    }
}
