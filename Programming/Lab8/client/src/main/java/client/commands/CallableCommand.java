package client.commands;

import client.exec.ModifiedLocaleException;
import client.managers.LocaleManager;
import client.transfer.SendReceiver;
import client.managers.User;

import common.commands.Command;
import common.transfer.*;
import common.ui.RouteBuilder;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class CallableCommand extends Command {
    public CallableCommand(String name, String[] args, String description) {
        super(name, args, description);
    }

    public String submit(String[] args) throws Exception {
        User user = User.getInstance();

        execute(args);

        Request request = new Request(RequestTask.EXECUTE_COMMAND, new CommandInfo(getName(), args, null), user.getUserInfo());
        Response response = SendReceiver.getInstance().sendAndReceive(request);

        return handleResponse(response);
    }

    public String submit(String[] args, RouteBuilder builder) throws Exception {
        User user = User.getInstance();

        execute(args);

        Request request = new Request(RequestTask.EXECUTE_COMMAND, new CommandInfo(getName(), args, builder), user.getUserInfo());
        Response response = SendReceiver.getInstance().sendAndReceive(request);

        return handleResponse(response);
    }

    public String handleResponse(Response response) throws ModifiedLocaleException {
        if (response.data() == null) return null;

        TagCarrier tagCarrier = (TagCarrier) response.data();

        if (!response.ok()) throw new ModifiedLocaleException(tagCarrier);

        Locale locale = LocaleManager.getInstance().getCurrentLocale();

        ResourceBundle commandBundle = ResourceBundle.getBundle("client.locale.Command", locale);

        String tag = tagCarrier.tag();
        Object data = tagCarrier.data();

        if (data == null) return commandBundle.getString(tag);
        else return commandBundle.getString(tag).formatted(data);
    }
}
