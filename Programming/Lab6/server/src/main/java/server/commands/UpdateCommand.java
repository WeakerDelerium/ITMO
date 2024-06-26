package server.commands;

import common.collection.Route;
import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;
import common.ui.RouteReader;
import common.ui.UserInput;
import common.transfer.Response;
import common.validators.RouteValidator;
import server.managers.CollectionManager;

public class UpdateCommand extends ServerCommand {
    private final CollectionManager collectionManager;
    private final UserInput userInput;

    public UpdateCommand(CollectionManager collectionManager, UserInput userInput) {
        super("update", new String[]{"id", "{element}"}, "update the value of a collection element whose id is equal to a given one");
        this.collectionManager = collectionManager;
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(1, args.length);

        String id = args[0];

        RouteValidator.validateId.validate(id);

        this.collectionManager.removeById(id);
        this.collectionManager.addToCollection(this.collectionManager.readNewRoute(this.userInput, id));

        Console.getInstance().println("Item was updated");
    }

    @Override
    public Response serverExecute(String[] args, RouteReader routeReader) {
        try {
            String id = args[0];

            if (routeReader == null) {
                this.collectionManager.validateId(id);

                return new Response(true, null);
            } else {
                Route route = this.collectionManager.readNewRoute(routeReader);

                this.collectionManager.removeById(id);
                this.collectionManager.addToCollection(route);

                return new Response(true, "Item with id (" + id + ") was updated");
            }
        } catch (Exception e) {
            return new Response(false, e);
        }
    }
}
