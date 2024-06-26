package server.commands;

import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;
import common.transfer.Response;
import common.ui.RouteReader;
import server.managers.CollectionManager;

import java.util.StringJoiner;

public class ShowCommand extends ServerCommand {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", null, "print to standard output all the elements of the collection in string representation");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.collectionManager.checkEmptyCollection()) console.println("Collection is empty");
        else this.collectionManager.getRouteCollection().forEach(console::println);
    }

    @Override
    public Response serverExecute(String[] args, RouteReader routeReader) {
        if (this.collectionManager.checkEmptyCollection()) return new Response(true, "Collection is empty");
        else {
            StringJoiner joiner = new StringJoiner("\n");

            this.collectionManager.getRouteCollection().forEach(route -> joiner.add(route.toString()));

            return new Response(true, joiner.toString());
        }
    }
}
