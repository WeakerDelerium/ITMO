package obj.enums;

public enum PlaceEnum {
    IN_POT("в горшок"),
    TO_TRAP("к западне"),
    FROM_VERY_DEEP_HOLE("из Очень Глубокой Ямы");

    private final String place;

    PlaceEnum(String place) {
        this.place = place;
    }

    public String getPlace() {
        return this.place;
    }

    @Override
    public String toString() {
        return getPlace();
    }
}
