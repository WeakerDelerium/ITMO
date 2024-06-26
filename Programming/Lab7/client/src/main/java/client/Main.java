package client;

import client.managers.AuthorizationManager;
import client.managers.User;
import client.commands.*;

import common.commands.HelpCommand;
import common.commands.HistoryCommand;
import common.managers.CommandManager;
import common.ui.CommandReader;
import common.ui.Console;
import common.ui.UserInput;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static Console console;

    public static void main(String[] args) {
        try {
            User user = new User("localhost", 54278);

            console = Console.getInstance();
            console.setScanner(new Scanner(System.in));

            UserInput userInput = new UserInput();

            user.setUserInfo(AuthorizationManager.authorize(user, userInput));

            CommandManager commandManager = initCommandManager(user, userInput);

            CommandReader commandReader = new CommandReader(user.getUserInfo().username(), commandManager);

            console.println("Plotni wassssssupchik --- Author - @pxdkxvan --- Version - 1.0\n");

            commandReader.init();
        } catch (IOException e) {
            console.printError("connection to server failed");
        } catch (ClassNotFoundException e) {
            console.printError("error while casting serialized object");
        } catch (NoSuchAlgorithmException e) {
            console.printError("error while hashing serialized object");
        }
    }

    private static CommandManager initCommandManager(User user, UserInput userInput) {
        CommandManager commandManager = new CommandManager();

        commandManager.setCommands(new ArrayList<>(Arrays.asList(
                new AddCommand(user, userInput),
                new AddIfMinCommand(user, userInput),
                new ClearCommand(user),
                new ExecuteScriptCommand(commandManager),
                new ExitCommand(userInput),
                new GroupCountingByDistanceCommand(user),
                new HelpCommand(commandManager),
                new HistoryCommand(commandManager),
                new InfoCommand(user),
                new PrintDescendingCommand(user),
                new PrintFieldDescendingDistanceCommand(user),
                new RemoveByIdCommand(user),
                new RemoveGreaterCommand(user, userInput),
                new ShowCommand(user),
                new UpdateCommand(user, userInput)
        )));

        return commandManager;
    }
}
