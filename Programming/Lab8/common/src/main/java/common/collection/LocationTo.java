package common.collection;

import java.io.Serializable;
import java.util.Objects;

/**
 * @param x    Поле не может быть null
 * @param y    Поле не может быть null
 * @param name Длина строки не должна быть больше 675, Поле не может быть null
 */
public record LocationTo(double x, int y, String name) implements Serializable {
    @Override
    public String toString() {
        return "%f\t\t%d\t\t%s".formatted(x(), y(), name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationTo oth = (LocationTo) o;
        return Objects.equals(x, oth.x) && Objects.equals(y, oth.y) && Objects.equals(name, oth.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }
}
