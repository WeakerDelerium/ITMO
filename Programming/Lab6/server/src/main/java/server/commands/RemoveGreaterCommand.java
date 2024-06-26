package server.commands;

import common.collection.Route;
import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;
import common.ui.RouteReader;
import common.ui.UserInput;
import common.transfer.Response;
import server.managers.CollectionManager;

public class RemoveGreaterCommand extends ServerCommand {
    private final CollectionManager collectionManager;
    private final UserInput userInput;

    public RemoveGreaterCommand(CollectionManager collectionManager, UserInput userInput) {
        super("remove_greater", null, "remove from a collection all elements greater than a given value");
        this.collectionManager = collectionManager;
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Route route = this.collectionManager.readNewRoute(this.userInput);

        Console.getInstance().println("Items removed: " + this.collectionManager.removeGreater(route));
    }

    @Override
    public Response serverExecute(String[] args, RouteReader routeReader) {
        Route route = this.collectionManager.readNewRoute(routeReader);

        return new Response(true, "Items removed: " + this.collectionManager.removeGreater(route));
    }
}
