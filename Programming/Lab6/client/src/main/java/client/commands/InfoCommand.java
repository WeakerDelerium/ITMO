package client.commands;

import client.modules.Client;

import common.exception.WrongNumberOfArgumentsException;
import common.transfer.Request;

import java.io.IOException;

public class InfoCommand extends UserCommand {
    private final Client client;
    public InfoCommand(Client client) {
        super("info", null, "print information about the collection to the standard output stream.");
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
