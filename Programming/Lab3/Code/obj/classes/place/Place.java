package obj.classes.place;

import obj.enums.PlaceEnum;
import obj.interfaces.PlaceInterface;

public abstract class Place implements PlaceInterface {
    private final PlaceEnum place;

    public Place(PlaceEnum place) {
        this.place = place;
    }

    public String getPlaceName() {
        return this.place.toString();
    }

    @Override
    public String toString() {
        return getPlaceName();
    }
}
