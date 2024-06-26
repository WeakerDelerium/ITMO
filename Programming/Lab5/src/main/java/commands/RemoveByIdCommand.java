package commands;

import exception.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.Console;
import validators.RouteValidator;

@Modifiable
public class RemoveByIdCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", new String[]{"id"}, "remove an element from a collection by its id");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException, IllegalArgumentException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(1, args.length);

        String id = args[0];

        RouteValidator.validateId.validate(id);

        this.collectionManager.removeById(id);

        Console.println("Item was removed");
    }
}
