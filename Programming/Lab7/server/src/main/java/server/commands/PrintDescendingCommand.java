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

public class PrintDescendingCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public PrintDescendingCommand() {
        super("print_descending", null, "display collection elements in descending order");
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.collectionManager.checkEmptyCollection()) console.println("Collection is empty");
        else this.collectionManager.descendingCollection().forEach(console::println);
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

            this.collectionManager.descendingCollection().forEach(route -> joiner.add(route.toString()));

            return new Response(true, joiner.toString());
        }
    }
}
