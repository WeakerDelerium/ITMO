package client.commands;

import client.modules.Client;

import common.exception.WrongNumberOfArgumentsException;
import common.transfer.Request;

import java.io.IOException;

public class ShowCommand extends UserCommand {
    private final Client client;

    public ShowCommand(Client client) {
        super("show", null, "print to standard output all the elements of the collection in string representation");
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
