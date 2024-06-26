package common.collection;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private final long x; //Максимальное значение поля: 304
    private final double y;

    public Coordinates(long x, double y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return String.format(
                "{\n    X: %d\n    Y: %f\n  }",
                getX(), getY()
        );
    }
}
