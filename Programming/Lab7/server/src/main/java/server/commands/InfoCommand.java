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

public class InfoCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public InfoCommand() {
        super("info", null, "print information about the collection to the standard output stream.");
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console.getInstance().println(this.collectionManager.getCollectionInfo());
    }

    @Override
    public Response serverExecute(String[] args, RouteBuilder routeBuilder, UserInfo userInfo)
            throws SQLException, NoSuchAlgorithmException {
        String username = userInfo.username();
        String passwd = userInfo.passwd();

        if (!DBExecutor.checkUserExists(username, passwd)) return new Response(false, new UserAuthorizationException());

        return new Response(true, this.collectionManager.getCollectionInfo());
    }
}
