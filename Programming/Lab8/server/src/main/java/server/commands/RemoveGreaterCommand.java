package server.commands;

import common.collection.Route;
import common.transfer.TagCarrier;
import common.transfer.UserInfo;
import common.ui.RouteBuilder;
import common.transfer.Response;

import server.DB.DBExecutor;
import server.exec.CommandNotSupportedException;
import server.managers.CollectionManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class RemoveGreaterCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public RemoveGreaterCommand() {
        super("remove_greater", null, "remove from a collection all elements greater than a given value");
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

        if (!DBExecutor.checkUserExists(username, passwd)) return new Response(false, new TagCarrier("userInfoLost", null));

        Route route = this.collectionManager.readNewRoute(routeBuilder);

        return new Response(true, new TagCarrier("removeMany", this.collectionManager.removeGreater(route, username)));
    }
}
