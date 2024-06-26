package server.commands;

import common.exception.UserAccessException;
import common.exception.UserAuthorizationException;
import common.exception.WrongNumberOfArgumentsException;
import common.transfer.UserInfo;
import common.ui.Console;
import common.transfer.Response;
import common.ui.RouteBuilder;
import common.validators.RouteValidator;
import server.DB.DBExecutor;
import server.managers.CollectionManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class RemoveByIdCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public RemoveByIdCommand() {
        super("remove_by_id", new String[]{"id"}, "remove an element from a collection by its id");
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, IllegalArgumentException, SQLException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(1, args.length);

        String id = args[0];
        RouteValidator.validateId.validate(id);
        this.collectionManager.validateRouteId(Integer.parseInt(id));

        this.collectionManager.removeById(Integer.parseInt(id));

        Console.getInstance().println("Item was removed");
    }

    @Override
    public Response serverExecute(String[] args, RouteBuilder routeBuilder, UserInfo userInfo)
            throws SQLException, NoSuchAlgorithmException {
        try {
            String username = userInfo.username();
            String passwd = userInfo.passwd();

            if (!DBExecutor.checkUserExists(username, passwd)) throw new UserAuthorizationException();

            String id = args[0];

            RouteValidator.validateId.validate(id);
            this.collectionManager.validateRouteId(Integer.parseInt(id));
            if (!DBExecutor.checkUserAccess(username, Integer.parseInt(id))) throw new UserAccessException();

            this.collectionManager.removeById(Integer.parseInt(id));

            return new Response(true, "Item was removed");
        } catch (IllegalArgumentException e) {
            return new Response(false, e);
        }
    }
}
