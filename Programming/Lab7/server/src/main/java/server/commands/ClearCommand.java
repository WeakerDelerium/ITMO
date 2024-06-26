package server.commands;

import common.exception.UserAuthorizationException;
import common.exception.UserNotFoundException;
import common.exception.WrongNumberOfArgumentsException;
import common.transfer.UserInfo;
import common.ui.Console;
import common.transfer.Response;
import common.ui.RouteBuilder;
import server.DB.DBExecutor;
import server.managers.CollectionManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class ClearCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public ClearCommand() {
        super("clear", null, "clear collection (remove all collection elements)");
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, SQLException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console console = Console.getInstance();
        if (this.collectionManager.checkEmptyCollection())
            console.println("Collection is empty");
        else {
            this.collectionManager.clearCollection();
            console.println("Collection was cleared");
        }
    }

    @Override
    public Response serverExecute(String[] args, RouteBuilder routeBuilder, UserInfo userInfo)
            throws SQLException, NoSuchAlgorithmException {
        String username = userInfo.username();
        String passwd = userInfo.passwd();

        if (!DBExecutor.checkUserExists(username, passwd)) return new Response(false, new UserAuthorizationException());

        if (this.collectionManager.checkEmptyUserCollection(username))
            return new Response(true, "Collection is empty");
        else {
            this.collectionManager.clearUserCollection(username);
            return new Response(true, "Your items was cleared");
        }
    }
}
