package obj.classes;

import obj.enums.ActionEnum;
import obj.interfaces.ActionInterface;
import obj.interfaces.EntityInterface;

public abstract class Entity implements EntityInterface, ActionInterface {

    private final String name;
    private ActionEnum action;


    public Entity(String name, ActionEnum actionEnum) {
        this.name = name;
        this.action = actionEnum;
    }


    public String getEntityName() {
        return this.name;
    }


    public String getEntityAction() {
        return this.action.toString();
    }


    public void setEntityAction(ActionEnum actionEnum) {
        this.action = actionEnum;
    }


    @Override
    public String toString() {
        return getEntityName() + " " + getEntityAction();
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

        int hash = 0;

        for (int i = 0; i < getEntityName().length(); i++) {
            hash += getEntityName().charAt(i) * i;
        }

        for (int i = 0; i < getEntityAction().length(); i++) {
            hash += getEntityAction().charAt(i) * i;
        }

        return hash;
    }
}
