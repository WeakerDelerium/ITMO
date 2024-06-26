package common.collection;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

/**
 * @param id           Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
 * @param name         Поле не может быть null, Строка не может быть пустой
 * @param coordinates  Поле не может быть null
 * @param creationDate Поле не может быть null, Значение этого поля должно генерироваться автоматически
 * @param from         Поле не может быть null
 * @param to           Поле не может быть null
 * @param distance     Значение поля должно быть больше 1
 */
public record Route(Integer id, String name, Coordinates coordinates, LocalDate creationDate, LocationFrom from, LocationTo to, Long distance) implements Comparable<Route>, Serializable {
    @Override
    public int compareTo(Route obj) {
        return Comparator.comparingLong(Route::distance).thenComparingInt(Route::id).compare(this, obj);
    }

    @Override
    public String toString() {
        return "%d\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%d".formatted(this.id, this.name, this.coordinates, this.creationDate, this.from, this.to, this.distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id) && Objects.equals(name, route.name) && Objects.equals(coordinates, route.coordinates) && Objects.equals(creationDate, route.creationDate) && Objects.equals(from, route.from) && Objects.equals(to, route.to) && Objects.equals(distance, route.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, from, to, distance);
    }
}
