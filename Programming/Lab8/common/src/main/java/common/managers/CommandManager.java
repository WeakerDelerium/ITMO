package common.managers;

import common.commands.*;
import common.exec.CommandNotFoundException;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CommandManager implements Cloneable {
    private final LinkedHashMap<String, Command> commandList;

    public CommandManager() {
        this.commandList = new LinkedHashMap<>();
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commandList.clear();
        commands.forEach(command -> this.commandList.put(command.getName(), command));
    }

    public void executeCommand(String command, String[] arguments) throws Exception {
        if (!this.commandList.containsKey(command)) throw new CommandNotFoundException(command);

        this.commandList.get(command).execute(arguments);
    }

    public LinkedHashMap<String, Command> getCommandList() {
        return this.commandList;
    }

    @Override
    public CommandManager clone() {
        try {
            return (CommandManager) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
