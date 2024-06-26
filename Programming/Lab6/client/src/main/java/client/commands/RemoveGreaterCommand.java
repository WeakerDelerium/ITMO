package client.commands;

import client.modules.Client;

import common.exception.WrongNumberOfArgumentsException;
import common.ui.RouteAsker;
import common.ui.RouteReader;
import common.ui.UserInput;
import common.transfer.Request;

import java.io.IOException;

public class RemoveGreaterCommand extends ComplexCommand {
    private final Client client;
    private final UserInput userInput;

    public RemoveGreaterCommand(Client client, UserInput userInput) {
        super("remove_greater", null, "remove from a collection all elements greater than a given value");
        this.client = client;
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, IOException, ClassNotFoundException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        RouteAsker routeAsker = new RouteAsker(this.userInput);
        routeAsker.ask();

        RouteReader routeReader = routeAsker.getRouteReader();

        this.client.writeObject(new Request(getName(), args, routeReader));

        getResponse(this.client);
    }

    @Override
    public void scriptExecute(String[] args, RouteReader routeReader)
            throws WrongNumberOfArgumentsException, IOException, ClassNotFoundException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        this.client.writeObject(new Request(getName(), args, routeReader));

        getResponse(this.client);
    }
}
