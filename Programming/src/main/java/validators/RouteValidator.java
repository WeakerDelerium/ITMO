package validators;

import parsers.DateParser;
import parsers.NumParser;
import parsers.Parser;

public class RouteValidator {
    private static void userInputValidate(String arg, Parser parser, String incorrectInput) {
        try {
            parser.parse(arg);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(incorrectInput);
        }
    }

    private static void userInputValidate(Boolean validateCondition, String incorrectValue) {
        if (!validateCondition) throw new IllegalArgumentException(incorrectValue);
    }

    public static final Validator validateId = id -> {
        try {
            userInputValidate(Integer.parseInt(id) > 0, "Id must be greater than zero");
        } catch (Exception e) {
            throw new IllegalArgumentException("Id must be an integer");
        }
    };

    public static final Validator validateName = name ->
            userInputValidate(!name.trim().isEmpty(), "Name can't be empty");

    public static final Validator validateCoordinateX = coordinateX -> {
        try {
            userInputValidate(Long.parseLong(coordinateX) <= 304, "Coordinate X can't be greater than 304");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Coordinate X must be an integer");
        }
    };

    public static final Validator validateCoordinateY = coordinateY ->
            userInputValidate(coordinateY, NumParser.doubleParser, "Coordinate Y must be a fractional number");

    public static final Validator validateCreationDate = creationDate ->
            userInputValidate(creationDate, DateParser.dateParser, "Wrong date format");

    public static final Validator validateFromX = fromX ->
            userInputValidate(fromX, NumParser.integerParser, "Location-from coordinate X must be an integer");

    public static final Validator validateFromY = fromY ->
            userInputValidate(fromY, NumParser.integerParser, "Location-from coordinate Y must be an integer");

    public static final Validator validateFromZ = fromZ ->
            userInputValidate(fromZ, NumParser.longParser, "Location-from coordinate Z must be an integer");

    public static final Validator validateFromName = fromName ->
            userInputValidate(!fromName.trim().isEmpty(), "Location-from name can't be empty");

    public static final Validator validateToX = toX ->
            userInputValidate(toX, NumParser.doubleParser, "Location-to coordinate X must be a fractional number");

    public static final Validator validateToY = toY ->
            userInputValidate(toY, NumParser.integerParser, "Location-to coordinate Y must be a fractional number");

    public static final Validator validateToName = toName -> {
        try {
            userInputValidate(toName.length() <= 675 && !toName.trim().isEmpty(), "Location-to name can't be greater than 675");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Location-to name can't be empty");
        }
    };

    public static final Validator validateDistance = distance -> {
        try {
            userInputValidate(Long.parseLong(distance) > 1, "Distance must be greater than 1");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Distance must be an integer");
        }
    };
}
