package client.commands;

import client.managers.User;

import common.exception.WrongNumberOfArgumentsException;
import common.transfer.CommandInfo;
import common.transfer.Request;
import common.transfer.RequestTask;
import common.transfer.Response;
import common.ui.RouteAsker;
import common.ui.RouteBuilder;
import common.ui.UserInput;
import common.validators.RouteValidator;

import java.io.IOException;

public class UpdateCommand extends ComplexCommand {
    private final User user;
    private final UserInput userInput;

    public UpdateCommand(User user, UserInput userInput) {
        super("update", new String[]{"id", "{element}"}, "update the value of a collection element whose id is equal to a given one");
        this.user = user;
        this.userInput = userInput;
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, IOException, ClassNotFoundException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(1, args.length);

        String id = args[0];

        RouteValidator.validateId.validate(id);

        Request request = new Request(RequestTask.EXECUTE_COMMAND, new CommandInfo(getName(), args, null), this.user.getUserInfo());

        this.user.writeObject(request);

        Response response = this.user.readObject();

        if (response.ok()) {
            RouteAsker routeAsker = new RouteAsker(this.userInput);
            routeAsker.ask();

            RouteBuilder routeBuilder = routeAsker.getRouteBuilder();
            routeBuilder.setId(id);

            request = new Request(RequestTask.EXECUTE_COMMAND, new CommandInfo(getName(), args, routeBuilder), this.user.getUserInfo());

            this.user.writeObject(request);

            getResponse(this.user);
        } else throw (RuntimeException) response.data();
    }

    @Override
    public void scriptExecute(String[] args, RouteBuilder routeBuilder)
            throws WrongNumberOfArgumentsException, IOException, ClassNotFoundException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(1, args.length);

        String id = args[0];

        RouteValidator.validateId.validate(id);

        routeBuilder.setId(id);

        Request request = new Request(RequestTask.EXECUTE_COMMAND, new CommandInfo(getName(), args, routeBuilder), this.user.getUserInfo());

        this.user.writeObject(request);

        getResponse(this.user);
    }
}
