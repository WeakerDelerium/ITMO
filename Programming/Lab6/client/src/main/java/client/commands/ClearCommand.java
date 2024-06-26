package client.commands;

import client.modules.Client;

import common.exception.WrongNumberOfArgumentsException;
import common.transfer.Request;

import java.io.IOException;

public class ClearCommand extends UserCommand {
    private final Client client;

    public ClearCommand(Client client) {
        super("clear", null, "clear collection (remove all collection elements)");
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
