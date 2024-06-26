package client.commands;

import client.modules.Client;

import common.exception.WrongNumberOfArgumentsException;
import common.transfer.Request;
import common.validators.RouteValidator;

import java.io.IOException;

public class RemoveByIdCommand extends UserCommand {
    private final Client client;

    public RemoveByIdCommand(Client client) {
        super("remove_by_id", new String[]{"id"}, "remove an element from a collection by its id");
        this.client = client;
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, IllegalArgumentException, IOException, ClassNotFoundException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(1, args.length);

        String id = args[0];

        RouteValidator.validateId.validate(id);

        this.client.writeObject(new Request(getName(), args, null));

        getResponse(this.client);
    }
}
