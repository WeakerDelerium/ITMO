package parsers;

@FunctionalInterface
public interface Parser {
    Object parse(String s);
}
