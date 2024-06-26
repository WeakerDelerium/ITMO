package client.commands;

import client.modules.Client;

import common.exception.WrongNumberOfArgumentsException;
import common.transfer.Response;
import common.ui.RouteAsker;
import common.ui.RouteReader;
import common.ui.UserInput;
import common.transfer.Request;
import common.validators.RouteValidator;

import java.io.IOException;

public class UpdateCommand extends ComplexCommand {
    private final Client client;
    private final UserInput userInput;

    public UpdateCommand(Client client, UserInput userInput) {
        super("update", new String[]{"id", "{element}"}, "update the value of a collection element whose id is equal to a given one");
        this.client = client;
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, IOException, ClassNotFoundException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(1, args.length);

        String id = args[0];

        RouteValidator.validateId.validate(id);

        this.client.writeObject(new Request(getName(), args, null));

        Response response = this.client.readObject();

        if (response.ok()) {
            RouteAsker routeAsker = new RouteAsker(this.userInput);
            routeAsker.ask();

            RouteReader routeReader = routeAsker.getRouteReader();
            routeReader.setId(id);

            this.client.writeObject(new Request(getName(), args, routeReader));

            getResponse(this.client);
        } else throw (RuntimeException) response.data();
    }

    @Override
    public void scriptExecute(String[] args, RouteReader routeReader)
            throws WrongNumberOfArgumentsException, IOException, ClassNotFoundException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(1, args.length);

        String id = args[0];

        RouteValidator.validateId.validate(id);

        routeReader.setId(id);

        this.client.writeObject(new Request(getName(), args, routeReader));

        getResponse(this.client);
    }
}
