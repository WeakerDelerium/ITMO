package common.validators;

public class AuthorizationValidator {
    public static final Validator validateUsername = username -> {
        if (username.trim().isEmpty()) throw new IllegalArgumentException("username cannot be empty");
    };

    public static final Validator validatePasswd = passwd -> {
        if (passwd.trim().isEmpty()) throw new IllegalArgumentException("passwd cannot be empty");
        if (passwd.trim().length() < 8) throw new IllegalArgumentException("minimum password length - 8");
    };
}
