package common.validators;

import common.parsers.DateParser;
import common.parsers.NumParser;
import common.parsers.Parser;

public class RouteValidator {
    private static void userInputValidate(String arg, Parser parser, String tag) {
        try {
            parser.parse(arg);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(tag);
        }
    }

    private static void userInputValidate(Boolean validateCondition, String tag) {
        if (!validateCondition) throw new IllegalArgumentException(tag);
    }

    public static final Validator validateId = id -> {
        try {
            userInputValidate(Integer.parseInt(id) > 0, "idCheck");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("idFormat");
        }
    };

    public static final Validator validateName = name ->
            userInputValidate(!name.trim().isEmpty(), "nameFormat");

    public static final Validator validateCoordinateX = coordinateX -> {
        try {
            userInputValidate(Long.parseLong(coordinateX) <= 300, "coordXCheck");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("coordXFormat");
        }
    };

    public static final Validator validateCoordinateY = coordinateY ->
            userInputValidate(coordinateY, NumParser.doubleParser, "coordYFormat");

    public static final Validator validateCreationDate = creationDate ->
            userInputValidate(creationDate, DateParser.dateParser, "dateFormat");

    public static final Validator validateFromX = fromX ->
            userInputValidate(fromX, NumParser.integerParser, "fromXFormat");

    public static final Validator validateFromY = fromY ->
            userInputValidate(fromY, NumParser.integerParser, "fromYFormat");

    public static final Validator validateFromZ = fromZ ->
            userInputValidate(fromZ, NumParser.longParser, "fromZFormat");

    public static final Validator validateFromName = fromName ->
            userInputValidate(!fromName.trim().isEmpty(), "fromNameFormat");

    public static final Validator validateToX = toX ->
            userInputValidate(toX, NumParser.doubleParser, "toXFormat");

    public static final Validator validateToY = toY ->
            userInputValidate(toY, NumParser.integerParser, "toYFormat");

    public static final Validator validateToName = toName -> {
        try {
            userInputValidate(toName.length() <= 675 && !toName.trim().isEmpty(), "toNameCheck");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("toNameFormat");
        }
    };

    public static final Validator validateDistance = distance -> {
        try {
            userInputValidate(Long.parseLong(distance) > 1, "distanceCheck");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("distanceFormat");
        }
    };
}
