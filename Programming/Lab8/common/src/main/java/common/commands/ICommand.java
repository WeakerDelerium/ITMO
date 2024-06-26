package common.commands;

public interface ICommand {
    void execute(String[] args) throws Exception;
    String getName();
    String[] getArgs();
    String getDescription();
}
