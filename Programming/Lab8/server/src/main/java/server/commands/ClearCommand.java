package server.commands;

import common.transfer.TagCarrier;
import common.transfer.UserInfo;
import common.transfer.Response;
import common.ui.RouteBuilder;

import server.DB.DBExecutor;
import server.exec.CommandNotSupportedException;
import server.managers.CollectionManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class ClearCommand extends ServerCommand {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public ClearCommand() {
        super("clear", null, "clear collection (remove all collection elements)");
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

        if (this.collectionManager.checkEmptyUserCollection(username))
            return new Response(true, new TagCarrier("clearFailed", null));
        else {
            this.collectionManager.clearUserCollection(username);
            return new Response(true, new TagCarrier("clearSuccess", null));
        }
    }
}
