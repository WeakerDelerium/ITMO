package common.collection;

import java.io.Serializable;

/**
 * @param x Максимальное значение поля: 304
 */
public record Coordinates(long x, double y) implements Serializable {
    @Override
    public String toString() {
        return String.format(
                "{\n    X: %d\n    Y: %f\n  }",
                x(), y()
        );
    }
}
