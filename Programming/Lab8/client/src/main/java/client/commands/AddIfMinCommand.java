package client.commands;

import client.exec.MethodImpracticabilityException;

public class AddIfMinCommand extends CallableCommand implements ComplexCommand {
    public AddIfMinCommand() {
        super("add_if_min", new String[]{"{element}"}, "add a new element to a collection if its value is less than the smallest element of this collection");
    }

    @Override
    public void execute(String[] args) {}

    @Override
    public String submit(String[] args) throws Exception {
        throw new MethodImpracticabilityException();
    }
}
