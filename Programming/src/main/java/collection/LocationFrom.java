package collection;

public class LocationFrom {
    private final int x;
    private final int y;
    private final long z;
    private final String name; //Строка не может быть пустой, Поле может быть null

    public LocationFrom(int x, int y, long z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public long getZ() {
        return this.z;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format(
                "{\n    X: %d\n    Y: %d\n    Z: %d\n    Name: %s\n  }",
                getX(), getY(), getZ(), getName()
        );
    }
}
