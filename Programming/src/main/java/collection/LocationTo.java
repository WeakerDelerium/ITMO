package collection;

public class LocationTo {
    private final Double x; //Поле не может быть null
    private final Integer y; //Поле не может быть null
    private final String name; //Длина строки не должна быть больше 675, Поле не может быть null

    public LocationTo(Double x, Integer y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Double getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format(
                "{\n    X: %f\n    Y: %d\n    Name: %s\n  }",
                getX(), getY(), getName()
        );
    }
}
