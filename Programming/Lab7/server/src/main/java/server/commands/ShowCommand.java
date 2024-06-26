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

public class ShowCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public ShowCommand() {
        super("show", null, "print to standard output all the elements of the collection in string representation");
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console.getInstance().println(this.collectionManager.getCollectionString());
    }

    @Override
    public Response serverExecute(String[] args, RouteBuilder routeBuilder, UserInfo userInfo)
            throws SQLException, NoSuchAlgorithmException {
        String username = userInfo.username();
        String passwd = userInfo.passwd();

        if (!DBExecutor.checkUserExists(username, passwd)) throw new UserAuthorizationException();

        return new Response(true, this.collectionManager.getCollectionString());
    }
}
