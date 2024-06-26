package server.commands;

import common.exception.WrongNumberOfArgumentsException;
import common.transfer.Response;
import common.ui.RouteReader;
import server.managers.CollectionManager;
import common.ui.Console;
import common.ui.UserInput;

public class AddCommand extends ServerCommand {
    private final CollectionManager collectionManager;
    private final UserInput userInput;

    public AddCommand(CollectionManager collectionManager,
                      UserInput userInput) {
        super("add", new String[]{"{element}"}, "add a new element to the collection");
        this.collectionManager = collectionManager;
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        this.collectionManager.addToCollection(this.collectionManager.readNewRoute(this.userInput));

        Console.getInstance().println("Item was added");
    }

    @Override
    public Response serverExecute(String[] args, RouteReader routeReader) {
        this.collectionManager.addToCollection(this.collectionManager.readNewRoute(routeReader));

        return new Response(true, "Item was added");
    }
}
