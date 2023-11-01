package obj.abclasses;

import obj.enums.ActionEnum;
import obj.interfaces.ActionInterface;
import obj.interfaces.NameInterface;

public abstract class Entity implements NameInterface, ActionInterface {

    private final String name;
    private ActionEnum action;


    public Entity(String name, ActionEnum actionEnum) {
        this.name = name;
        this.action = actionEnum;
    }


    public String getName() {
        return this.name;
    }


    public String getAction() {
        return this.action.toString();
    }


    public void setAction(ActionEnum actionEnum) {
        this.action = actionEnum;
    }


    @Override
    public String toString() {
        return getName() + " " + getAction();
    }


    @Override
    public boolean equals(Object object) {

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        return (this == object || hashCode() == object.hashCode());
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
