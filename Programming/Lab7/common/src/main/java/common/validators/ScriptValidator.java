package common.validators;

import java.util.HashSet;

public class ScriptValidator {
    public static HashSet<String> scriptHistory = new HashSet<>();

    public static final Validator validateRecursion = script -> {
        if (scriptHistory.contains(script)) throw new IllegalArgumentException("Script recursion");
    };
}
