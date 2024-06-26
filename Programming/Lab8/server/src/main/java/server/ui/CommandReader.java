package server.ui;

import common.managers.CommandManager;

import java.io.IOException;
import java.util.Arrays;

public class CommandReader {
    private final String user;
    private final Console console;
    private final CommandManager commandManager;

    public CommandReader(String user, CommandManager commandManager) {
        this.user = user;
        this.console = Console.getInstance();
        this.commandManager = commandManager;
    }

    public void init() {
        this.console.print(this.user + " >>> ");
        String input = this.console.input().trim();

        if (input.isEmpty()) return;

        String[] params = input.split(" ");

        String command = params[0];
        String[] arguments = Arrays.copyOfRange(params, 1, params.length);

        try {
            this.commandManager.executeCommand(command, arguments);
        } catch (IOException e) {
            this.console.printError(e.getMessage());
            System.exit(0);
        } catch (Exception e) {
            this.console.printError(e.getMessage());
        }

        init();
    }
}
