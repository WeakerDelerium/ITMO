package server.commands;

import common.transfer.TagCarrier;
import common.transfer.UserInfo;
import common.transfer.Response;
import common.ui.RouteBuilder;
import common.validators.RouteValidator;

import server.DB.DBExecutor;
import server.exec.CommandNotSupportedException;
import server.managers.CollectionManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class RemoveByIdCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public RemoveByIdCommand() {
        super("remove_by_id", new String[]{"id"}, "remove an element from a collection by its id");
    }

    @Override
    public void execute(String[] args) {
        throw new CommandNotSupportedException();
    }

    @Override
    public Response serverExecute(String[] args, RouteBuilder routeBuilder, UserInfo userInfo)
            throws SQLException, NoSuchAlgorithmException {
        try {
            String username = userInfo.username();
            String passwd = userInfo.passwd();

            if (!DBExecutor.checkUserExists(username, passwd)) return new Response(false, new TagCarrier("userInfoLost", null));

            String id = args[0];

            RouteValidator.validateId.validate(id);
            this.collectionManager.validateRouteId(Integer.parseInt(id));
            if (!DBExecutor.checkUserAccess(username, Integer.parseInt(id))) throw new IllegalArgumentException("idNotYour");

            this.collectionManager.removeById(Integer.parseInt(id));

            return new Response(true, new TagCarrier("removeOne", null));
        } catch (IllegalArgumentException e) {
            return new Response(false, new TagCarrier(e.getMessage(), null));
        }
    }
}
