package client.commands;

import client.exec.MethodImpracticabilityException;

public class AddCommand extends CallableCommand implements ComplexCommand {
    public AddCommand() {
        super("add", new String[]{"{element}"}, "add a new element to the collection");
    }

    @Override
    public void execute(String[] args) {}

    @Override
    public String submit(String[] args) throws Exception {
        throw new MethodImpracticabilityException();
    }
}