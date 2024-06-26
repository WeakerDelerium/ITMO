package common.collection;

import java.io.Serializable;

/**
 * @param x    Поле не может быть null
 * @param y    Поле не может быть null
 * @param name Длина строки не должна быть больше 675, Поле не может быть null
 */
public record LocationTo(Double x, Integer y, String name) implements Serializable {
    @Override
    public String toString() {
        return String.format(
                "{\n    X: %f\n    Y: %d\n    Name: %s\n  }",
                x(), y(), name()
        );
    }
}
