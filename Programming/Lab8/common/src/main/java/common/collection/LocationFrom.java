package common.collection;

import java.io.Serializable;
import java.util.Objects;

/**
 * @param name Строка не может быть пустой, Поле может быть null
 */
public record LocationFrom(int x, int y, long z, String name) implements Serializable {
    @Override
    public String toString() {
        return "%d\t\t%d\t\t%d\t\t%s".formatted(x(), y(), z(), name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationFrom oth = (LocationFrom) o;
        return x == oth.x && y == oth.y && z == oth.z && Objects.equals(name, oth.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }
}
