package server.commands;

import common.collection.Route;
import common.exception.UserAuthorizationException;
import common.exception.WrongNumberOfArgumentsException;
import common.transfer.UserInfo;
import common.ui.Console;
import common.ui.RouteBuilder;
import common.ui.UserInput;
import common.transfer.Response;
import server.DB.DBExecutor;
import server.managers.CollectionManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class RemoveGreaterCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    private final UserInput userInput;

    public RemoveGreaterCommand(UserInput userInput) {
        super("remove_greater", null, "remove from a collection all elements greater than a given value");
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, SQLException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Route route = this.collectionManager.readNewRoute(this.userInput);

        Console.getInstance().println("Items removed: " + this.collectionManager.removeGreater(route));
    }

    @Override
    public Response serverExecute(String[] args, RouteBuilder routeBuilder, UserInfo userInfo)
            throws SQLException, NoSuchAlgorithmException {
        String username = userInfo.username();
        String passwd = userInfo.passwd();

        if (!DBExecutor.checkUserExists(username, passwd)) throw new UserAuthorizationException();

        Route route = this.collectionManager.readNewRoute(routeBuilder);

        return new Response(true, "Items removed: " + this.collectionManager.removeGreater(route, username));
    }
}
