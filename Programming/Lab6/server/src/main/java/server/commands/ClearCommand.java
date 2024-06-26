package server.commands;

import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;
import common.transfer.Response;
import common.ui.RouteReader;
import server.managers.CollectionManager;

public class ClearCommand extends ServerCommand {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", null, "clear collection (remove all collection elements)");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.collectionManager.checkEmptyCollection()) console.println("Collection is empty");
        else {
            this.collectionManager.clearCollection();

            console.println("Collection was cleared");
        }
    }

    @Override
    public Response serverExecute(String[] args, RouteReader routeReader) {
        if (this.collectionManager.checkEmptyCollection()) return new Response(true, "Collection is empty");
        else {
            this.collectionManager.clearCollection();

            return new Response(true, "Collection was cleared");
        }
    }
}
