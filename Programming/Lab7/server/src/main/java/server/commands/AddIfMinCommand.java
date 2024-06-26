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

public class AddIfMinCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    private final UserInput userInput;

    public AddIfMinCommand(UserInput userInput) {
        super("add_if_min", new String[]{"{element}"}, "add a new element to a collection if its value is less than the smallest element of this collection");
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, IllegalArgumentException, SQLException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Route newRoute = this.collectionManager.readNewRoute(this.userInput);

        Console console = Console.getInstance();
        if (!this.collectionManager.isMinElement(newRoute))
            console.println("The minimum distance condition was not met, item wasn't added");
        else {
            this.collectionManager.addRoute(newRoute);
            console.println("Item was added");
        }
    }

    @Override
    public Response serverExecute(String[] args, RouteBuilder routeBuilder, UserInfo userInfo)
            throws SQLException, NoSuchAlgorithmException {
        String username = userInfo.username();
        String passwd = userInfo.passwd();

        if (!DBExecutor.checkUserExists(username, passwd)) return new Response(false, new UserAuthorizationException());

        Route newRoute = this.collectionManager.readNewRoute(routeBuilder);

        if (!this.collectionManager.isMinElement(newRoute))
            return new Response(true, "The minimum distance condition was not met, item wasn't added");
        else {
            this.collectionManager.addRoute(newRoute, username);
            return new Response(true,"Item was added");
        }
    }
}
