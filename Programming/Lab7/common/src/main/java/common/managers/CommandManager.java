package common.managers;

import common.commands.*;
import common.exception.CommandNotFoundException;
import common.exception.ScriptFormatException;
import common.exception.WrongNumberOfArgumentsException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class CommandManager implements Cloneable {
    private final int COMMAND_HISTORY_LIMIT = 14;
    private final LinkedBlockingDeque<String> commandHistory = new LinkedBlockingDeque<>(COMMAND_HISTORY_LIMIT);

    private final LinkedHashMap<String, Command> commandList;

    public CommandManager() {
        this.commandList = new LinkedHashMap<>();
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commandList.clear();
        commands.forEach(command -> this.commandList.put(command.getName(), command));
    }

    public void executeCommand(String command, String[] arguments) throws WrongNumberOfArgumentsException,
            InterruptedException, CommandNotFoundException, IOException, ScriptFormatException, ClassNotFoundException, SQLException {
        if (!this.commandList.containsKey(command)) throw new CommandNotFoundException(command);

        this.commandList.get(command).execute(arguments);

        addToHistory(command);
    }

    public void addToHistory(String command) throws InterruptedException {
        if (this.commandHistory.remainingCapacity() == 0) this.commandHistory.take();
        this.commandHistory.put(command);
    }

    public LinkedHashMap<String, Command> getCommandList() {
        return this.commandList;
    }

    public LinkedBlockingDeque<String> getCommandHistory() {
        return this.commandHistory;
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
