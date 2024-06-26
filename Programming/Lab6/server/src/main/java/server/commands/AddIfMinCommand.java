package server.commands;

import common.collection.Route;
import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;
import common.ui.RouteReader;
import common.ui.UserInput;
import common.transfer.Response;
import server.managers.CollectionManager;

public class AddIfMinCommand extends ServerCommand {
    private final CollectionManager collectionManager;
    private final UserInput userInput;

    public AddIfMinCommand(CollectionManager collectionManager, UserInput userInput) {
        super("add_if_min", new String[]{"{element}"}, "add a new element to a collection if its value is less than the smallest element of this collection");
        this.collectionManager = collectionManager;
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException, IllegalArgumentException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Route newRoute = this.collectionManager.readNewRoute(this.userInput);

        Console console = Console.getInstance();
        if (this.collectionManager.isMinElement(newRoute))
            console.println("The minimum distance condition was not met, item wasn't added");
        else {
            this.collectionManager.addToCollection(newRoute);
            console.println("Item was added");
        }
    }

    @Override
    public Response serverExecute(String[] args, RouteReader routeReader) {
        Route newRoute = this.collectionManager.readNewRoute(routeReader);

        if (this.collectionManager.isMinElement(newRoute))
            return new Response(true, "The minimum distance condition was not met, item wasn't added");
        else {
            this.collectionManager.addToCollection(newRoute);
            return new Response(true,"Item was added");
        }
    }
}
