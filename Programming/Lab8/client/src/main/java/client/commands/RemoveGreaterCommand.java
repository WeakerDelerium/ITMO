package client.commands;

import client.exec.MethodImpracticabilityException;

public class RemoveGreaterCommand extends CallableCommand implements ComplexCommand {
    public RemoveGreaterCommand() {
        super("remove_greater", null, "remove from a collection all elements greater than a given value");
    }

    @Override
    public void execute(String[] args) {}

    @Override
    public String submit(String[] args) throws Exception {
        throw new MethodImpracticabilityException();
    }
}
