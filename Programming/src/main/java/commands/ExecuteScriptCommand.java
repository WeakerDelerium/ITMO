package commands;

import exception.CommandNotFoundException;
import exception.ScriptFormatException;
import exception.WrongNumberOfArgumentsException;
import managers.CommandManager;
import managers.Console;
import validators.ScriptValidator;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ExecuteScriptCommand extends Command {
    private final CommandManager commandManager;

    public ExecuteScriptCommand(CommandManager commandManager) {
        super("execute_script", new String[]{"file_name"}, "read and execute the script from the specified file. The script contains commands in the same form in which the user enters them interactively.");
        this.commandManager = commandManager;
    }

    private Scanner loadScript(String filename) throws FileNotFoundException {
        try {
            return new Scanner(new InputStreamReader(new FileInputStream(filename)));
        } catch (Exception e) {
            throw new FileNotFoundException("Script file wasn't found");
        }
    }

    private HashMap<String, String> loadRouteArgs(Scanner scriptLines) throws ScriptFormatException {
        HashMap<String, String> newRouteArgs = new HashMap<>();

        try {
            newRouteArgs.put("name", scriptLines.nextLine());
            newRouteArgs.put("coordinates x", scriptLines.nextLine());
            newRouteArgs.put("coordinates y", scriptLines.nextLine());
            newRouteArgs.put("from x", scriptLines.nextLine());
            newRouteArgs.put("from y", scriptLines.nextLine());
            newRouteArgs.put("from z", scriptLines.nextLine());
            newRouteArgs.put("from name", scriptLines.nextLine());
            newRouteArgs.put("to x", scriptLines.nextLine());
            newRouteArgs.put("to y", scriptLines.nextLine());
            newRouteArgs.put("to name", scriptLines.nextLine());
            newRouteArgs.put("distance", scriptLines.nextLine());
        } catch (Exception e) {
            throw new ScriptFormatException();
        }

        return newRouteArgs;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException, IOException,
            InterruptedException, CommandNotFoundException, ScriptFormatException {
        if (args.length != 1) throw new WrongNumberOfArgumentsException(0, args.length);

        String filename = args[0];

        ScriptValidator.validateRecursion.validate(filename);
        ScriptValidator.scriptHistory.add(filename);

        Scanner scriptLines = loadScript(filename);

        while (scriptLines.hasNextLine()) {
            String line = scriptLines.nextLine().trim();

            if (line.isEmpty()) continue;

            String[] params = line.split(" ");

            String command = params[0];
            String[] arguments = Arrays.copyOfRange(params, 1, params.length);

            HashMap<String, ComplexCommand> compositeArgsCommandList = this.commandManager.getComplexCommandList();
            if (compositeArgsCommandList.containsKey(command)) {
                compositeArgsCommandList.get(command).scriptExecute(arguments, loadRouteArgs(scriptLines));
                this.commandManager.addToTmp(command);
            } else this.commandManager.executeCommand(command, arguments);
        }

        ScriptValidator.scriptHistory.remove(filename);

        Console.println("Script " + filename + " was executed");
    }
}
