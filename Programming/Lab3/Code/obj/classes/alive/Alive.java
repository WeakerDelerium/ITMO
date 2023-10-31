package obj.classes.alive;

import obj.classes.Entity;
import obj.enums.ActionEnum;

public abstract class Alive extends Entity {
    public Alive(String name, ActionEnum action) {
        super(name, action);
    }

    public String say(String message) {
        return ": \"" + message + "\"";
    }
}
