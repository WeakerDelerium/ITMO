package obj.classes;

import obj.enums.PlaceEnum;
import obj.interfaces.NameInterface;

public class Place implements NameInterface {
    private final PlaceEnum place;

    public Place(PlaceEnum place) {
        this.place = place;
    }

    @Override
    public String getName() {
        return this.place.toString();
    }

    @Override
    public String toString() {
        return getName();
    }
}
