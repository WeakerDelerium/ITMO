package server.commands;

import common.commands.Command;
import common.transfer.Response;
import common.transfer.UserInfo;
import common.ui.RouteBuilder;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public abstract class ServerCommand extends Command {
    public ServerCommand(String name, String[] args, String description) {
        super(name, args, description);
    }

    public abstract Response serverExecute(String[] args, RouteBuilder routeBuilder, UserInfo userInfo) throws SQLException, NoSuchAlgorithmException;
}
