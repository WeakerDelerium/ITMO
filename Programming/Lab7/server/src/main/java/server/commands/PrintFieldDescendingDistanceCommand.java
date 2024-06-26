package server.commands;

import common.exception.UserAuthorizationException;
import common.exception.WrongNumberOfArgumentsException;
import common.transfer.UserInfo;
import common.ui.Console;
import common.transfer.Response;
import common.ui.RouteBuilder;
import server.DB.DBExecutor;
import server.managers.CollectionManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.StringJoiner;

public class PrintFieldDescendingDistanceCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public PrintFieldDescendingDistanceCommand() {
        super("print_field_descending_distance", null, "display the values of the distance field of all elements in descending order");
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.collectionManager.checkEmptyCollection()) console.println("Collection is empty");
        else this.collectionManager.descendingCollection().forEach(route -> console.println(route.getDistance()));
    }

    @Override
    public Response serverExecute(String[] args, RouteBuilder routeBuilder, UserInfo userInfo)
            throws SQLException, NoSuchAlgorithmException {
        String username = userInfo.username();
        String passwd = userInfo.passwd();

        if (!DBExecutor.checkUserExists(username, passwd)) return new Response(false, new UserAuthorizationException());

        if (this.collectionManager.checkEmptyCollection())
            return new Response(true, "Collection is empty");
        else {
            StringJoiner joiner = new StringJoiner("\n");

            this.collectionManager.descendingCollection().forEach(route -> joiner.add(String.valueOf(route.getDistance())));

            return new Response(true, joiner.toString());
        }
    }
}
