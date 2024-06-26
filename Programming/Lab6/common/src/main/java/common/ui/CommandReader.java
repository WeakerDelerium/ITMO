package common.ui;

import common.managers.CommandManager;
import common.validators.ScriptValidator;

import java.util.Arrays;

public class CommandReader {
    private final String user;
    private final Console console;
    private final CommandManager commandManager;

    public CommandReader(String user, Console console, CommandManager commandManager) {
        this.user = user;
        this.console = console;
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
        } catch (Exception e) {
            e.printStackTrace();
            this.console.printError(e.getMessage());
            ScriptValidator.scriptHistory.clear();
        }
    }
}
