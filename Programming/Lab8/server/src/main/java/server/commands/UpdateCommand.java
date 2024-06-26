package server.commands;

import common.collection.Route;
import common.transfer.TagCarrier;
import common.transfer.UserInfo;
import common.ui.RouteBuilder;
import common.transfer.Response;
import common.validators.RouteValidator;

import server.DB.DBExecutor;
import server.exec.CommandNotSupportedException;
import server.managers.CollectionManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UpdateCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public UpdateCommand() {
        super("update", new String[]{"id", "ITEM"}, "update the value of a collection element whose id is equal to a given one");
    }

    @Override
    public void execute(String[] args) {
        throw new CommandNotSupportedException();
    }

    @Override
    public Response serverExecute(String[] args, RouteBuilder routeBuilder, UserInfo userInfo)
            throws SQLException, NoSuchAlgorithmException {
        String username = userInfo.username();
        String passwd = userInfo.passwd();

        if (!DBExecutor.checkUserExists(username, passwd)) return new Response(false, "userInfoLost");

        try {
            String id = args[0];

            RouteValidator.validateId.validate(id);
            this.collectionManager.validateRouteId(Integer.parseInt(id));
            if (!DBExecutor.checkUserAccess(username, Integer.parseInt(id))) throw new IllegalArgumentException("idNotYour");

            if (routeBuilder == null)
                return new Response(true, null);
            else {
                Route route = this.collectionManager.readNewRoute(routeBuilder);

                this.collectionManager.updateRoute(route, username);

                return new Response(true, new TagCarrier("update", id));
            }
        } catch (Exception e) {
            return new Response(false, new TagCarrier(e.getMessage(), null));
        }
    }
}
