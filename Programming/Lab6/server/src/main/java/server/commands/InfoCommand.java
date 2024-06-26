package server.commands;

import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;
import common.transfer.Response;
import common.ui.RouteReader;
import server.managers.CollectionManager;

public class InfoCommand extends ServerCommand {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", null, "print information about the collection to the standard output stream.");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console.getInstance().println(this.collectionManager.getCollectionInfo());
    }

    @Override
    public Response serverExecute(String[] args, RouteReader routeReader) {
        return new Response(true, this.collectionManager.getCollectionInfo());
    }
}
