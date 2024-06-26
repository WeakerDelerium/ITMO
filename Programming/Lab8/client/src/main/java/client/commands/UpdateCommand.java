package client.commands;

import client.transfer.SendReceiver;
import client.managers.User;

import common.transfer.CommandInfo;
import common.transfer.Request;
import common.transfer.RequestTask;
import common.transfer.Response;
import common.ui.RouteBuilder;
import common.validators.RouteValidator;

public class UpdateCommand extends CallableCommand implements ComplexCommand {
    public UpdateCommand() {
        super("update", new String[]{"id", "{element}"}, "update the value of a collection element whose id is equal to a given one");
    }

    @Override
    public void execute(String[] args) {}

    @Override
    public String submit(String[] args, RouteBuilder builder) throws Exception {
        User user = User.getInstance();

        String id = args[0];
        RouteValidator.validateId.validate(id);
        builder.setId(id);

        Request request = new Request(RequestTask.EXECUTE_COMMAND, new CommandInfo(getName(), args, builder), user.getUserInfo());
        Response response = SendReceiver.getInstance().sendAndReceive(request);

        return handleResponse(response);
    }
}
