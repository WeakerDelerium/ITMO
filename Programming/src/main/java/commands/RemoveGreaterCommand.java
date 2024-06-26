package commands;

import exception.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.Console;
import managers.UserInputManager;

import java.util.HashMap;

@Modifiable
public class RemoveGreaterCommand extends ComplexCommand {
    private final CollectionManager collectionManager;
    private final UserInputManager userInputManager;

    public RemoveGreaterCommand(CollectionManager collectionManager, UserInputManager userInputManager) {
        super("remove_greater", null, "remove from a collection all elements greater than a given value");
        this.collectionManager = collectionManager;
        this.userInputManager = userInputManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        this.collectionManager.removeGreater(this.collectionManager.readNewRoute(this.userInputManager));
    }

    @Override
    public void scriptExecute(String[] args, HashMap<String, String> routeScriptArgs) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Console.println("Removed collection items: " +
                this.collectionManager.removeGreater(this.collectionManager.readNewRoute(this.userInputManager)));
    }
}
