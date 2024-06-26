package client.commands;

import common.managers.CommandManager;
import common.commands.Command;
import common.exception.CommandNotFoundException;
import common.exception.ScriptFormatException;
import common.exception.WrongNumberOfArgumentsException;
import common.ui.Console;
import common.ui.RouteReader;
import common.validators.ScriptValidator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private RouteReader loadRouteArgs(Scanner scriptLines) throws ScriptFormatException {
        RouteReader routeReader = new RouteReader();

        try {
            routeReader.setName(
                    scriptLines.nextLine()
            );
            routeReader.setCoordinates(
                    scriptLines.nextLine(),
                    scriptLines.nextLine()
            );
            routeReader.setFrom(
                    scriptLines.nextLine(),
                    scriptLines.nextLine(),
                    scriptLines.nextLine(),
                    scriptLines.nextLine()
            );
            routeReader.setTo(
                    scriptLines.nextLine(),
                    scriptLines.nextLine(),
                    scriptLines.nextLine()
            );
            routeReader.setDistance(
                    scriptLines.nextLine()
            );
        } catch (Exception e) {
            throw new ScriptFormatException();
        }

        return routeReader;
    }

    @Override
    public void execute(String[] args) throws WrongNumberOfArgumentsException, IOException,
            InterruptedException, CommandNotFoundException, ScriptFormatException, ClassNotFoundException {
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

            HashMap<String, Command> commandList = this.commandManager.getCommandList();
            if (commandList.containsKey(command) && commandList.get(command) instanceof ComplexCommand) {
                ((ComplexCommand) commandList.get(command)).scriptExecute(arguments, loadRouteArgs(scriptLines));
            } else this.commandManager.executeCommand(command, arguments);
        }

        ScriptValidator.scriptHistory.remove(filename);

        Console.getInstance().println("\nScript " + filename + " was executed\n");
    }
}
