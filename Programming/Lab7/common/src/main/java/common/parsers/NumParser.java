package common.parsers;

public class NumParser {
    public static final Parser integerParser = Integer::valueOf;
    public static final Parser longParser = Long::valueOf;
    public static final Parser doubleParser = Double::valueOf;
}
