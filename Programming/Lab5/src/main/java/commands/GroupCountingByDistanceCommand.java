package commands;

import exception.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.Console;

public class GroupCountingByDistanceCommand extends Command {
    private final CollectionManager collectionManager;

    public GroupCountingByDistanceCommand(CollectionManager collectionManager) {
        super("group_counting_by_distance", null, "group the collection elements by the value of the distance field, display the number of elements in each group");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        if (this.collectionManager.getRouteCollection().isEmpty()) Console.println("Collection is empty");
        else this.collectionManager.groupByDistance().forEach((distance, routes) ->
                Console.println(String.format("%d: %d", distance, routes.size())));
    }
}
