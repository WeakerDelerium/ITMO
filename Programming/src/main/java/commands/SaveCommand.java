package commands;

import exception.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.Console;
import managers.FileManager;

import java.io.IOException;

public class SaveCommand extends Command {
    private final CollectionManager collectionManager;
    private final FileManager fileManager;

    public SaveCommand(CollectionManager collectionManager, FileManager fileManager) {
        super("save", null, "save the collection to a file");
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException, IOException {
        if (args.length != 0) throw new WrongNumberOfArgumentsException(0, args.length);

        this.fileManager.write(this.collectionManager.getDataCollection());

        Console.println("Collection was saved");
    }
}
