package obj.abclasses;

import obj.exceptions.NameException;
import obj.interfaces.NameInterface;

import java.util.Objects;

public abstract class Entity implements NameInterface {
    private final String name;

    public Entity(String name) {
        if (name.isEmpty()) {
            throw new NameException("Сущность должна иметь имя");
        }
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return getName();
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
        return Objects.hash(super.hashCode(), getName());
    }
}
