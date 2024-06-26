package server.commands;

import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;
import common.transfer.Response;
import common.ui.RouteReader;
import server.managers.CollectionManager;

import java.util.StringJoiner;

public class PrintFieldDescendingDistanceCommand extends ServerCommand {
    private final CollectionManager collectionManager;

    public PrintFieldDescendingDistanceCommand(CollectionManager collectionManager) {
        super("print_field_descending_distance", null, "display the values of the distance field of all elements in descending order");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.collectionManager.checkEmptyCollection()) console.println("Collection is empty");
        else this.collectionManager.descendingCollection().forEach(route -> console.println(route.getDistance()));
    }

    @Override
    public Response serverExecute(String[] args, RouteReader routeReader) {
        if (this.collectionManager.checkEmptyCollection()) return new Response(true, "Collection is empty");
        else {
            StringJoiner joiner = new StringJoiner("\n");

            this.collectionManager.descendingCollection().forEach(route -> joiner.add(String.valueOf(route.getDistance())));

            return new Response(true, joiner.toString());
        }
    }
}
