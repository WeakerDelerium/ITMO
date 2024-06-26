package common.commands;

import java.util.Objects;

public abstract class Command implements ICommand {
    private final String name;
    private final String[] args;
    private final String description;

    public Command(String name, String[] args, String description) {
        this.name = name;
        this.args = args;
        this.description = description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String[] getArgs() {
        return this.args;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return Objects.equals(getName(), command.name) && Objects.equals(getDescription(), command.description);
    }

    @Override
    public String toString() {
        if (this.args == null) return String.format("`%s` - %s", getName(), getDescription());
        return String.format("`%s %s` - %s", getName(), String.join(" ", getArgs()), getDescription());
    }
}
