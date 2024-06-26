package client;

import client.modules.Client;
import client.commands.*;

import common.commands.HelpCommand;
import common.commands.HistoryCommand;
import common.managers.CommandManager;
import common.ui.CommandReader;
import common.ui.Console;
import common.ui.UserInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 51246);

            UserInput userInput = new UserInput();
            CommandManager commandManager = initCommandManager(client, userInput);

            Console console = Console.getInstance();
            console.setScanner(new Scanner(System.in));

            CommandReader commandReader = new CommandReader(
                    client.getChannel().getRemoteAddress().toString(), console, commandManager);

            console.println("Plotni wassssssupchik --- Author - @pxdkxvan --- Version - 1.0\n");

            for (; ; ) {
                commandReader.init();
            }
        } catch (IOException e) {
            Console.getInstance().printError("Connection to server failed");
        }
    }

    private static CommandManager initCommandManager(Client client, UserInput userInput) {
        CommandManager commandManager = new CommandManager();

        commandManager.setCommands(new ArrayList<>(Arrays.asList(
                new AddCommand(client, userInput),
                new AddIfMinCommand(client, userInput),
                new ClearCommand(client),
                new ExecuteScriptCommand(commandManager),
                new ExitCommand(userInput),
                new GroupCountingByDistanceCommand(client),
                new HelpCommand(commandManager),
                new HistoryCommand(commandManager),
                new InfoCommand(client),
                new PrintDescendingCommand(client),
                new PrintFieldDescendingDistanceCommand(client),
                new RemoveByIdCommand(client),
                new RemoveGreaterCommand(client, userInput),
                new ShowCommand(client),
                new UpdateCommand(client, userInput)
        )));

        return commandManager;
    }
}
