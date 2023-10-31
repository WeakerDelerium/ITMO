package obj.enums;

public enum ActionEnum {
    DEFAULT(""),
    PUSH_IN("сунул мордочку"),
    LICK("как следует лизнул"),
    MAKE_SURE("окончательно убедился в этом"),
    CARRY("понес горшок"),
    LOOK_OUT("выглянул"),
    ASK("спросил"),
    LOOK_IN("заглянул"),
    SAY("сказал"),
    WAS_TRUTH("было правдой");

    private final String action;

    ActionEnum(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }

    @Override
    public String toString() {
        return getAction();
    }
}
