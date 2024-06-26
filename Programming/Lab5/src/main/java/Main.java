import com.opencsv.exceptions.CsvException;
import managers.*;
import validators.ScriptValidator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) {
        try {
            String filename = args[0];

            CollectionManager collectionManager = new CollectionManager();
            UserInputManager userInputManager = new UserInputManager();
            FileManager fileManager = new FileManager(filename);
            CommandManager commandManager = new CommandManager(collectionManager, userInputManager, fileManager);

            Console.println("Welcome!!!\nAuthor - @pxdkxvan\nVersion - 1.1\n");

            try {
                loadData(commandManager, collectionManager, userInputManager, fileManager);
            } catch (Exception e) {
                collectionManager.load(fileManager.read());
            }

            for (; ; ) {
                Console.print(">>> ");
                String input = Console.input().trim();

                if (input.isEmpty()) continue;

                String[] params = input.split(" ");

                String command = params[0];
                String[] arguments = Arrays.copyOfRange(params, 1, params.length);

                try {
                    commandManager.executeCommand(command, arguments);
                } catch (Exception e) {
                    Console.printError(e.getMessage());
                    ScriptValidator.scriptHistory.clear();
                }
            }
        } catch (FileNotFoundException e) {
            Console.printError("File not found");
        } catch (CsvException e) {
            Console.printError(e.getMessage());
        } catch (IOException e) {
            Console.printError("File not available");
        }
    }

    private static void loadData(CommandManager commandManager, CollectionManager collectionManager,
                                 UserInputManager userInputManager, FileManager fileManager)
            throws CsvException, IOException {
        LinkedHashMap<String, ArrayList<String>> localData = commandManager.getLocalDataFileManager().read();
        if (userInputManager.readLocalData().equalsIgnoreCase("YES")) collectionManager.load(localData);
        else {
            Files.delete(Path.of("tmp.csv"));
            collectionManager.load(fileManager.read());
        }
    }
}