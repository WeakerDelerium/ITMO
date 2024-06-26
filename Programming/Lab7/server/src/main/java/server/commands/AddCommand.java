package server.commands;

import common.exception.UserAuthorizationException;
import common.exception.WrongNumberOfArgumentsException;
import common.transfer.Response;
import common.transfer.UserInfo;
import common.ui.RouteBuilder;
import common.ui.Console;
import common.ui.UserInput;

import server.DB.DBExecutor;
import server.managers.CollectionManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class AddCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    private final UserInput userInput;

    public AddCommand(UserInput userInput) {
        super("add", new String[]{"{element}"}, "add a new element to the collection");
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, SQLException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        this.collectionManager.addRoute(this.collectionManager.readNewRoute(this.userInput));

        Console.getInstance().println("Item was added");
    }

    @Override
    public Response serverExecute(String[] args, RouteBuilder routeBuilder, UserInfo userInfo)
            throws SQLException, NoSuchAlgorithmException {
        String username = userInfo.username();
        String passwd = userInfo.passwd();

        if (!DBExecutor.checkUserExists(username, passwd)) return new Response(false, new UserAuthorizationException());

        this.collectionManager.addRoute(this.collectionManager.readNewRoute(routeBuilder), username);

        return new Response(true, "Item was added");
    }
}
