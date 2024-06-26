package client.commands;

import client.managers.User;

import common.exception.WrongNumberOfArgumentsException;
import common.transfer.CommandInfo;
import common.transfer.Request;
import common.transfer.RequestTask;

import java.io.IOException;

public class ClearCommand extends UserCommand {
    private final User user;

    public ClearCommand(User user) {
        super("clear", null, "clear collection (remove all collection elements)");
        this.user = user;
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, IOException, ClassNotFoundException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Request request = new Request(RequestTask.EXECUTE_COMMAND, new CommandInfo(getName(), args, null), this.user.getUserInfo());

        this.user.writeObject(request);

        getResponse(this.user);
    }
}
