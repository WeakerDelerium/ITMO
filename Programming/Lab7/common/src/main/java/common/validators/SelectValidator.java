package common.validators;

public class SelectValidator {
    public static final Validator validateSelect = select -> {
        if (!select.equalsIgnoreCase("Y") && !select.equalsIgnoreCase("N"))
            throw new IllegalArgumentException(String.format("`%s` not expected", select));
    };
}
