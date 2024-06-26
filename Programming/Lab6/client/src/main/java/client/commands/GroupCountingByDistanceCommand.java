package client.commands;

import client.modules.Client;

import common.exception.WrongNumberOfArgumentsException;
import common.transfer.Request;

import java.io.IOException;

public class GroupCountingByDistanceCommand extends UserCommand {
    private final Client client;

    public GroupCountingByDistanceCommand(Client client) {
        super("group_counting_by_distance", null, "group the collection elements by the value of the distance field, display the number of elements in each group");
        this.client = client;
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, IOException, ClassNotFoundException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        this.client.writeObject(new Request(getName(), args, null));

        getResponse(this.client);
    }
}
