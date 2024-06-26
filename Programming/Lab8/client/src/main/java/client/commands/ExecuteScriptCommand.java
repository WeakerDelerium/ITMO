package client.commands;

import client.exec.MethodImpracticabilityException;

import client.exec.ScriptFormatException;
import common.ui.RouteBuilder;
import client.validators.ScriptValidator;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class ExecuteScriptCommand extends CallableCommand {
    private static final ArrayList<CallableCommand> commandList = new ArrayList<>(Arrays.asList(
            new AddCommand(), new AddIfMinCommand(), new ClearCommand(), new ExecuteScriptCommand(),
            new RemoveByIdCommand(), new RemoveGreaterCommand(), new UpdateCommand()));

    public ExecuteScriptCommand() {
        super("execute_script", new String[]{"file_name"}, "read and execute the script from the specified file. The script contains commands in the same form in which the user enters them interactively.");
    }

    private RouteBuilder getRouteBuilder(Scanner scriptLines) throws ScriptFormatException {
        RouteBuilder routeBuilder = new RouteBuilder();

        try {
            routeBuilder.setName(
                    scriptLines.nextLine()
            );
            routeBuilder.setCoordinates(
                    scriptLines.nextLine(),
                    scriptLines.nextLine()
            );
            routeBuilder.setFrom(
                    scriptLines.nextLine(),
                    scriptLines.nextLine(),
                    scriptLines.nextLine(),
                    scriptLines.nextLine()
            );
            routeBuilder.setTo(
                    scriptLines.nextLine(),
                    scriptLines.nextLine(),
                    scriptLines.nextLine()
            );
            routeBuilder.setDistance(
                    scriptLines.nextLine()
            );
        } catch (IllegalArgumentException e) {
            throw new ScriptFormatException();
        }

        return routeBuilder;
    }

    @Override
    public void execute(String[] args) {}

    @Override
    public String submit(String[] args) throws Exception {
         String filename = args[0];

        ScriptValidator.validateRecursion.validate(filename);
        ScriptValidator.scriptHistory.add(filename);

        Scanner scriptLines = new Scanner(new InputStreamReader(new FileInputStream(filename)));

        HashMap<String, CallableCommand> commandMap =
                new HashMap<>(commandList.stream().collect(Collectors.toMap(CallableCommand::getName, command -> command)));

        while (scriptLines.hasNextLine()) {
            String line = scriptLines.nextLine().trim();

            if (line.isEmpty()) continue;

            String[] params = line.split(" ");

            String command = params[0];
            String[] arguments = Arrays.copyOfRange(params, 1, params.length);

            if (!commandMap.containsKey(command)) throw new ScriptFormatException();
            else if (commandMap.get(command) instanceof ComplexCommand) commandMap.get(command).submit(arguments, getRouteBuilder(scriptLines));
            else commandMap.get(command).submit(arguments);
        }

        ScriptValidator.scriptHistory.remove(filename);

        return "Script was executed successfully";
    }

    @Override
    public String submit(String[] args, RouteBuilder builder) throws Exception {
        throw new MethodImpracticabilityException();
    }
}
