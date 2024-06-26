package common.collection;

import java.io.Serializable;
import java.time.LocalDate;

public class Route implements Comparable<Route>, Serializable {
    private final Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final LocationFrom from; //Поле не может быть null
    private final LocationTo to; //Поле не может быть null
    private final Long distance; //Значение поля должно быть больше 1

    public Route(Integer id, String name, Coordinates coordinates, LocalDate creationDate, LocationFrom from, LocationTo to, long distance) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public LocationFrom getFrom() {
        return this.from;
    }

    public LocationTo getTo() {
        return this.to;
    }

    public long getDistance() {
        return this.distance;
    }

    @Override
    public int compareTo(Route obj) {
        return Math.toIntExact(this.id - obj.getId());
    }

    @Override
    public String toString() {
        return String.format(
                "Route: {\n  Id: %d\n  Name: %s\n  Coordinates: %s\n  CreationDate: %s\n  From: %s\n  To: %s\n  Distance: %d\n}",
                getId(), getName(), getCoordinates(), getCreationDate(), getFrom(), getTo(), getDistance()
        );
    }
}
