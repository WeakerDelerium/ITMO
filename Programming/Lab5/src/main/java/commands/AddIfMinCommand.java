package commands;

import collection.Route;
import exception.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.Console;
import managers.UserInputManager;

import java.util.HashMap;

@Modifiable
public class AddIfMinCommand extends ComplexCommand {
    private final CollectionManager collectionManager;
    private final UserInputManager userInputManager;

    public AddIfMinCommand(CollectionManager collectionManager, UserInputManager userInputManager) {
        super("add_if_min", new String[]{"{element}"}, "add a new element to a collection if its value is less than the smallest element of this collection");
        this.collectionManager = collectionManager;
        this.userInputManager = userInputManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException, IllegalArgumentException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Route newRoute = this.collectionManager.readNewRoute(this.userInputManager);

        if (this.collectionManager.isMinElement(newRoute))
            Console.println("The minimum distance condition was not met, item wasn't added");
        else {
            this.collectionManager.addToCollection(newRoute);
            Console.println("Item was added");
        }
    }

    @Override
    public void scriptExecute(String[] args, HashMap<String, String> routeScriptArgs) throws WrongNumberOfArgumentsException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        Route newRoute = this.collectionManager.readNewRoute(this.userInputManager, routeScriptArgs);

        if (this.collectionManager.isMinElement(newRoute))
            Console.println("The minimum distance condition was not met, item wasn't added");
        else {
            this.collectionManager.addToCollection(newRoute);
            Console.println("Item was added");
        }
    }
}
