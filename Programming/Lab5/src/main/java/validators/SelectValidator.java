package validators;

public class SelectValidator {
    public static final Validator validateSelect = select -> {
        select = select.toUpperCase();
        if (!select.equals("YES") && !select.equals("NO"))
            throw new IllegalArgumentException(String.format("`%s` not expected", select));
    };
}
