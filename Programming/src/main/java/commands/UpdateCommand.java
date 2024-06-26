package commands;

import exception.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.Console;
import managers.UserInputManager;
import validators.RouteValidator;

import java.util.HashMap;

@Modifiable
public class UpdateCommand extends ComplexCommand {
    private final CollectionManager collectionManager;
    private final UserInputManager userInputManager;

    public UpdateCommand(CollectionManager collectionManager, UserInputManager userInputManager) {
        super("update", new String[]{"id", "{element}"}, "update the value of a collection element whose id is equal to a given one");
        this.collectionManager = collectionManager;
        this.userInputManager = userInputManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(1, args.length);

        String id = args[0];

        RouteValidator.validateId.validate(id);

        this.collectionManager.removeById(id);
        this.collectionManager.addToCollection(this.collectionManager.readNewRoute(this.userInputManager, id));

        Console.println("Item was updated");
    }

    @Override
    public void scriptExecute(String[] args, HashMap<String, String> routeScriptArgs) throws WrongNumberOfArgumentsException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(1, args.length);

        String id = args[0];

        RouteValidator.validateId.validate(id);

        routeScriptArgs.put("id", id);

        this.collectionManager.removeById(id);
        this.collectionManager.addToCollection(this.collectionManager.readNewRoute(this.userInputManager, routeScriptArgs));

        Console.println("Item was updated");
    }
}
