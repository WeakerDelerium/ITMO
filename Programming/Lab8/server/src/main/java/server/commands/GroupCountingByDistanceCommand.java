package server.commands;

import common.commands.Command;
import server.exec.WrongNumberOfArgumentsException;
import server.ui.Console;
import server.managers.CollectionManager;

public class GroupCountingByDistanceCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public GroupCountingByDistanceCommand() {
        super("group_counting_by_distance", null, "group the collection elements by the value of the distance field, display the number of elements in each group");
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.collectionManager.checkEmptyCollection())
            console.println("Collection is empty");
        else
            this.collectionManager.groupByDistance().forEach((distance, routes) ->
                console.println(String.format("%d: %d", distance, routes.size())));
    }
}
