package client.commands;

import client.modules.Client;

import common.exception.WrongNumberOfArgumentsException;
import common.transfer.Request;

import java.io.IOException;

public class PrintFieldDescendingDistanceCommand extends UserCommand {
    private final Client client;

    public PrintFieldDescendingDistanceCommand(Client client) {
        super("print_field_descending_distance", null, "display the values of the distance field of all elements in descending order");
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
