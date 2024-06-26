package client.commands;

import client.exec.MethodImpracticabilityException;
import common.ui.RouteBuilder;
import common.validators.RouteValidator;

public class RemoveByIdCommand extends CallableCommand {
    public RemoveByIdCommand() {
        super("remove_by_id", new String[]{"id"}, "remove an element from a collection by its id");
    }

    @Override
    public void execute(String[] args) {
        String id = args[0];
        RouteValidator.validateId.validate(id);
    }

    @Override
    public String submit(String[] args, RouteBuilder builder) throws Exception {
        throw new MethodImpracticabilityException();
    }
}
