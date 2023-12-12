package obj.enums;

public enum Details {
    DEFAULT(null),
    UPPER("верхняя"),
    PAPER("бумажная"),
    VERY_DEEP("Очень Глубокая");

    private final String detail;

    Details(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return this.detail;
    }

    @Override
    public String toString() {
        return getDetail();
    }
}
