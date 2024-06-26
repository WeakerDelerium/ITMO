package client.commands;

import client.managers.User;

import common.exception.WrongNumberOfArgumentsException;
import common.transfer.CommandInfo;
import common.transfer.Request;
import common.transfer.RequestTask;

import java.io.IOException;

public class GroupCountingByDistanceCommand extends UserCommand {
    private final User user;

    public GroupCountingByDistanceCommand(User user) {
        super("group_counting_by_distance", null, "group the collection elements by the value of the distance field, display the number of elements in each group");
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
