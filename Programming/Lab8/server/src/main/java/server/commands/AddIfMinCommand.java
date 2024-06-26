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

public class AddIfMinCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public AddIfMinCommand() {
        super("add_if_min", new String[]{"ITEM"}, "add a new element to a collection if its value is less than the smallest element of this collection");
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

        Route newRoute = this.collectionManager.readNewRoute(routeBuilder);

        if (!this.collectionManager.isMinElement(newRoute))
            return new Response(true, new TagCarrier("addFailed", null));
        else {
            this.collectionManager.addRoute(newRoute, username);
            return new Response(true, new TagCarrier("addSuccess", null));
        }
    }
}
