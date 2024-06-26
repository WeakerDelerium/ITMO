package server.commands;

import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;
import common.transfer.Response;
import common.ui.RouteReader;
import server.managers.CollectionManager;

import java.util.StringJoiner;

public class GroupCountingByDistanceCommand extends ServerCommand {
    private final CollectionManager collectionManager;

    public GroupCountingByDistanceCommand(CollectionManager collectionManager) {
        super("group_counting_by_distance", null, "group the collection elements by the value of the distance field, display the number of elements in each group");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.collectionManager.checkEmptyCollection()) console.println("Collection is empty");
        else this.collectionManager.groupByDistance().forEach((distance, routes) ->
                console.println(String.format("%d: %d", distance, routes.size())));
    }

    @Override
    public Response serverExecute(String[] args, RouteReader routeReader) {
        if (this.collectionManager.checkEmptyCollection()) return new Response(true, "Collection is empty");
        else {
            StringJoiner joiner = new StringJoiner("\n");

            this.collectionManager.groupByDistance().forEach(
                    (distance, routes) -> joiner.add(String.format("%d: %d", distance, routes.size())));

            return new Response(true, joiner.toString());
        }
    }
}
