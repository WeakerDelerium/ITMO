package client.commands;

import client.managers.User;

import common.exception.WrongNumberOfArgumentsException;
import common.transfer.CommandInfo;
import common.transfer.Request;
import common.transfer.RequestTask;
import common.ui.RouteAsker;
import common.ui.RouteBuilder;
import common.ui.UserInput;

import java.io.IOException;

public class AddCommand extends ComplexCommand {
    private final User user;
    private final UserInput userInput;

    public AddCommand(User user, UserInput userInput) {
        super("add", new String[]{"{element}"}, "add a new element to the collection");
        this.user = user;
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, IOException, ClassNotFoundException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        RouteAsker routeAsker = new RouteAsker(this.userInput);
        routeAsker.ask();

        RouteBuilder routeBuilder = routeAsker.getRouteBuilder();

        Request request = new Request(RequestTask.EXECUTE_COMMAND, new CommandInfo(getName(), args, routeBuilder), this.user.getUserInfo());

        this.user.writeObject(request);

        getResponse(this.user);
    }

    @Override
    public void scriptExecute(String[] args, RouteBuilder routeBuilder)
            throws WrongNumberOfArgumentsException, IOException, ClassNotFoundException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Request request = new Request(RequestTask.EXECUTE_COMMAND, new CommandInfo(getName(), args, routeBuilder), this.user.getUserInfo());

        this.user.writeObject(request);

        getResponse(this.user);
    }
}
