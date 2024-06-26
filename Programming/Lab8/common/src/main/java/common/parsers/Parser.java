package common.parsers;

@FunctionalInterface
public interface Parser {
    Object parse(String s);
}
