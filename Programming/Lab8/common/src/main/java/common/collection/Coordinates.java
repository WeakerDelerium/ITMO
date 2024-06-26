package common.collection;

import java.io.Serializable;
import java.util.Objects;

/**
 * @param x Максимальное значение поля: 300
 */
public record Coordinates(long x, double y) implements Serializable {
    @Override
    public String toString() {
        return "%d\t\t%f".formatted(x(), y());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates oth = (Coordinates) o;
        return x == oth.x && Double.compare(y, oth.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
