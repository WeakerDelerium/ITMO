package commands;

import exception.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.Console;
import managers.UserInputManager;

import java.util.HashMap;

@Modifiable
public class AddCommand extends ComplexCommand {
    private final CollectionManager collectionManager;
    private final UserInputManager userInputManager;

    public AddCommand(CollectionManager collectionManager,
                      UserInputManager userInputManager) {
        super("add", new String[]{"{element}"}, "add a new element to the collection");
        this.collectionManager = collectionManager;
        this.userInputManager = userInputManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        this.collectionManager.addToCollection(this.collectionManager.readNewRoute(this.userInputManager));

        Console.println("Item was added");
    }

    @Override
    public void scriptExecute(String[] args, HashMap<String, String> routeScriptArgs) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        this.collectionManager.addToCollection(this.collectionManager.readNewRoute(this.userInputManager, routeScriptArgs));

        Console.println("Item was added");
    }
}
