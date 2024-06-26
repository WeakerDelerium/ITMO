package server.commands;

import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;
import common.transfer.Response;
import common.ui.RouteReader;
import common.validators.RouteValidator;
import server.managers.CollectionManager;

public class RemoveByIdCommand extends ServerCommand {
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", new String[]{"id"}, "remove an element from a collection by its id");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException, IllegalArgumentException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(1, args.length);

        String id = args[0];

        RouteValidator.validateId.validate(id);

        this.collectionManager.removeById(id);

        Console.getInstance().println("Item was removed");
    }

    @Override
    public Response serverExecute(String[] args, RouteReader routeReader) {
        try {
            String id = args[0];

            RouteValidator.validateId.validate(id);

            this.collectionManager.removeById(id);

            return new Response(true, "Item was removed");
        } catch (Exception e) {
            return new Response(false, e);
        }
    }
}
