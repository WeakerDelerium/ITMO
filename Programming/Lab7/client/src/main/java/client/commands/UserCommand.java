package client.commands;

import client.managers.User;
import common.commands.Command;
import common.ui.Console;
import common.transfer.Response;

import java.io.IOException;

public abstract class UserCommand extends Command {
    public UserCommand(String name, String[] args, String description) {
        super(name, args, description);
    }

    protected void getResponse(User user) throws IOException, ClassNotFoundException {
        Response response = user.readObject();
        if (response.ok()) Console.getInstance().println(response.data());
        else throw (RuntimeException) response.data();
    }
}
