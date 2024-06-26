package managers;

import commands.*;
import exception.CommandNotFoundException;
import exception.ScriptFormatException;
import exception.WrongNumberOfArgumentsException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class CommandManager {
    private final int COMMAND_HISTORY_LIMIT = 14;
    private final LinkedBlockingDeque<String> commandHistory = new LinkedBlockingDeque<>(COMMAND_HISTORY_LIMIT);

    private final HelpCommand helpCommand;
    private final InfoCommand infoCommand;
    private final ShowCommand showCommand;
    private final AddCommand addCommand;
    private final UpdateCommand updateCommand;
    private final RemoveByIdCommand removeByIdCommand;
    private final ClearCommand clearCommand;
    private final SaveCommand saveCommand;
    private final ExecuteScriptCommand executeScriptCommand;
    private final ExitCommand exitCommand;
    private final AddIfMinCommand addIfMinCommand;
    private final RemoveGreaterCommand removeGreaterCommand;
    private final HistoryCommand historyCommand;
    private final GroupCountingByDistanceCommand groupCountingByDistanceCommand;
    private final PrintDescendingCommand printDescendingCommand;
    private final PrintFieldDescendingDistanceCommand printFieldDescendingDistanceCommand;

    private LinkedHashMap<String, Command> commandList;
    private LinkedHashMap<String, ComplexCommand> complexCommandList;

    private LinkedHashMap<String, Command> modifiableCommandList;

    private FileManager localDataFileManager;
    private CollectionManager localDataCollectionManager;

    public CommandManager(CollectionManager collectionManager, UserInputManager userInputManager, FileManager fileManager) {
        this.helpCommand = new HelpCommand(this);
        this.infoCommand = new InfoCommand(collectionManager);
        this.showCommand = new ShowCommand(collectionManager);
        this.addCommand = new AddCommand(collectionManager, userInputManager);
        this.updateCommand = new UpdateCommand(collectionManager, userInputManager);
        this.removeByIdCommand = new RemoveByIdCommand(collectionManager);
        this.clearCommand = new ClearCommand(collectionManager);
        this.saveCommand = new SaveCommand(collectionManager, fileManager);
        this.executeScriptCommand = new ExecuteScriptCommand(this);
        this.exitCommand = new ExitCommand(userInputManager);
        this.addIfMinCommand = new AddIfMinCommand(collectionManager, userInputManager);
        this.removeGreaterCommand = new RemoveGreaterCommand(collectionManager, userInputManager);
        this.historyCommand = new HistoryCommand(this);
        this.groupCountingByDistanceCommand = new GroupCountingByDistanceCommand(collectionManager);
        this.printDescendingCommand = new PrintDescendingCommand(collectionManager);
        this.printFieldDescendingDistanceCommand = new PrintFieldDescendingDistanceCommand(collectionManager);

        initCommandList();
        initCompositeArgsCommandList();
        initModifiableCommandList();

        initLocalData(collectionManager);
    }

    private void initCommandList() {
        this.commandList = new LinkedHashMap<>();

        this.commandList.put("help", this.helpCommand);
        this.commandList.put("info", this.infoCommand);
        this.commandList.put("show", this.showCommand);
        this.commandList.put("add", this.addCommand);
        this.commandList.put("update", this.updateCommand);
        this.commandList.put("remove_by_id", this.removeByIdCommand);
        this.commandList.put("clear", this.clearCommand);
        this.commandList.put("save", this.saveCommand);
        this.commandList.put("execute_script", this.executeScriptCommand);
        this.commandList.put("exit", this.exitCommand);
        this.commandList.put("add_if_min", this.addIfMinCommand);
        this.commandList.put("remove_greater", this.removeGreaterCommand);
        this.commandList.put("history", this.historyCommand);
        this.commandList.put("group_counting_by_distance", this.groupCountingByDistanceCommand);
        this.commandList.put("print_descending", this.printDescendingCommand);
        this.commandList.put("print_field_descending_distance", this.printFieldDescendingDistanceCommand);
    }

    private void initCompositeArgsCommandList() {
        this.complexCommandList = new LinkedHashMap<>();

        this.commandList.forEach((commandName, commandBody) -> {
            if (commandBody instanceof ComplexCommand)
                this.complexCommandList.put(commandName, (ComplexCommand) commandBody);
        });
    }

    private void initModifiableCommandList() {
        this.modifiableCommandList = new LinkedHashMap<>();

        this.commandList.forEach((commandName, commandBody) -> {
            if (commandBody.getClass().isAnnotationPresent(Modifiable.class))
                this.modifiableCommandList.put(commandName, commandBody);
        });
    }

    private void initLocalData(CollectionManager collectionManager) {
        this.localDataFileManager = new FileManager("tmp.csv");
        this.localDataCollectionManager = collectionManager;
    }

    public void executeCommand(String command, String[] arguments) throws WrongNumberOfArgumentsException,
            InterruptedException, CommandNotFoundException, IOException, ScriptFormatException {
        if (!this.commandList.containsKey(command)) throw new CommandNotFoundException(command);

        this.commandList.get(command).execute(arguments);

        addToHistory(command);
        addToTmp(command);
    }

    public void addToTmp(String command) throws IOException {
        if (this.modifiableCommandList.containsKey(command))
            this.localDataFileManager.write(this.localDataCollectionManager.getDataCollection());
    }

    public void addToHistory(String command) throws InterruptedException {
        if (this.commandHistory.remainingCapacity() == 0) this.commandHistory.take();
        this.commandHistory.put(command);
    }

    public LinkedHashMap<String, Command> getCommandList() {
        return this.commandList;
    }

    public LinkedHashMap<String, ComplexCommand> getComplexCommandList() {
        return this.complexCommandList;
    }

    public LinkedHashMap<String, Command> getModifiableCommandList() {
        return this.modifiableCommandList;
    }
    public LinkedBlockingDeque<String> getCommandHistory() {
        return this.commandHistory;
    }

    public FileManager getLocalDataFileManager() {
        return localDataFileManager;
    }
}
