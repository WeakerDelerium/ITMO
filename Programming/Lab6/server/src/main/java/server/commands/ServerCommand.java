package server.commands;

import common.commands.Command;
import common.transfer.Response;
import common.ui.RouteReader;

import java.util.HashMap;

public abstract class ServerCommand extends Command {
    public ServerCommand(String name, String[] args, String description) {
        super(name, args, description);
    }

    public abstract Response serverExecute(String[] args, RouteReader routeReader);
}
