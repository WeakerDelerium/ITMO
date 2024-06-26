package client.commands;

import client.exec.MethodImpracticabilityException;
import common.ui.RouteBuilder;

public class ClearCommand extends CallableCommand {
    public ClearCommand() {
        super("clear", null, "clear collection (remove all collection elements)");
    }

    @Override
    public void execute(String[] args) {}

    @Override
    public String submit(String[] args, RouteBuilder builder) throws Exception {
        throw new MethodImpracticabilityException();
    }
}
