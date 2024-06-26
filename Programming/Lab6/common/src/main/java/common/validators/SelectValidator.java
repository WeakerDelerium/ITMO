package common.validators;

public class SelectValidator {
    public static final Validator validateSelect = select -> {
        select = select.toUpperCase();
        if (!select.equals("Y") && !select.equals("N"))
            throw new IllegalArgumentException(String.format("`%s` not expected", select));
    };
}
