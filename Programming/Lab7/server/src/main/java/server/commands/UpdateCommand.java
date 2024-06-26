package server.commands;

import common.collection.Route;
import common.exception.UserAccessException;
import common.exception.UserAuthorizationException;
import common.exception.WrongNumberOfArgumentsException;
import common.transfer.UserInfo;
import common.ui.Console;
import common.ui.RouteBuilder;
import common.ui.UserInput;
import common.transfer.Response;
import common.validators.RouteValidator;

import server.DB.DBExecutor;
import server.managers.CollectionManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UpdateCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    private final UserInput userInput;

    public UpdateCommand(UserInput userInput) {
        super("update", new String[]{"id", "{element}"}, "update the value of a collection element whose id is equal to a given one");
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, SQLException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(1, args.length);

        String id = args[0];

        RouteValidator.validateId.validate(id);
        this.collectionManager.validateRouteId(Integer.parseInt(id));

        this.collectionManager.updateRoute(this.collectionManager.readNewRoute(this.userInput, id));

        this.collectionManager.removeRouteFromCollectionById(Integer.parseInt(id));
        this.collectionManager.addToCollection(this.collectionManager.readNewRoute(this.userInput, id));

        Console.getInstance().println("Item was updated");
    }

    @Override
    public Response serverExecute(String[] args, RouteBuilder routeBuilder, UserInfo userInfo)
            throws SQLException, NoSuchAlgorithmException {
        String username = userInfo.username();
        String passwd = userInfo.passwd();

        if (!DBExecutor.checkUserExists(username, passwd)) throw new UserAuthorizationException();

        try {
            String id = args[0];

            RouteValidator.validateId.validate(id);
            this.collectionManager.validateRouteId(Integer.parseInt(id));
            if (!DBExecutor.checkUserAccess(username, Integer.parseInt(id))) throw new UserAccessException();

            if (routeBuilder == null)
                return new Response(true, null);
            else {
                Route route = this.collectionManager.readNewRoute(routeBuilder);

                this.collectionManager.removeById(Integer.parseInt(id));
                this.collectionManager.addToCollection(route);

                return new Response(true, "Item with id (" + id + ") was updated");
            }
        } catch (Exception e) {
            return new Response(false, e);
        }
    }
}
