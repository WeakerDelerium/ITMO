package client.commands;

import client.managers.User;

import common.exception.WrongNumberOfArgumentsException;
import common.transfer.CommandInfo;
import common.transfer.Request;
import common.transfer.RequestTask;
import common.validators.RouteValidator;

import java.io.IOException;

public class RemoveByIdCommand extends UserCommand {
    private final User user;

    public RemoveByIdCommand(User user) {
        super("remove_by_id", new String[]{"id"}, "remove an element from a collection by its id");
        this.user = user;
    }

    @Override
    public void execute(String[] args)
            throws WrongNumberOfArgumentsException, IllegalArgumentException, IOException, ClassNotFoundException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(1, args.length);

        String id = args[0];

        RouteValidator.validateId.validate(id);

        Request request = new Request(RequestTask.EXECUTE_COMMAND, new CommandInfo(getName(), args, null), this.user.getUserInfo());

        this.user.writeObject(request);

        getResponse(this.user);
    }
}
