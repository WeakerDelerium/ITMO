package obj.classes;

import obj.abclasses.Entity;
import obj.enums.ActionEnum;

public class Alive extends Entity {
    public Alive(String name, ActionEnum action) {
        super(name, action);
    }

    public String say(String message) {
        return ": \"" + message + "\"";
    }
}
