package managers;

import parsers.Parser;
import parsers.NumParser;

import validators.SelectValidator;
import validators.Validator;
import validators.RouteValidator;

public class UserInputManager {
    private String inputRepeat(String message, Validator validator) {
        Console.print("> " + message);
        String value = Console.input().trim();

        try {
            validator.validate(value);
        } catch (IllegalArgumentException e) {
            Console.printError(e.getMessage());
            return inputRepeat(message, validator);
        }

        Console.println();
        return value;
    }

    private Object inputRepeat(String message, Validator validator, Parser parser) {
        Console.print("> " + message);
        String value = Console.input().trim();

        try {
            validator.validate(value);
        } catch (IllegalArgumentException e) {
            Console.printError(e.getMessage());
            return inputRepeat(message, validator, parser);
        }

        Console.println();
        return parser.parse(value);
    }

    public String readName() {
        return inputRepeat("Enter name: ", RouteValidator.validateName);
    }

    public Long readCoordinateX() {
        return (Long) inputRepeat("Enter coordinate X: ", RouteValidator.validateCoordinateX, NumParser.longParser);
    }

    public Double readCoordinateY() {
        return (Double) inputRepeat("Enter coordinate Y: ", RouteValidator.validateCoordinateY, NumParser.doubleParser);
    }

    public Integer readFromX() {
        return (Integer) inputRepeat("Enter location-from coordinate X: ", RouteValidator.validateFromX, NumParser.integerParser);
    }

    public Integer readFromY() {
        return (Integer) inputRepeat("Enter location-from coordinate Y: ", RouteValidator.validateFromY, NumParser.integerParser);
    }

    public Long readFromZ() {
        return (Long) inputRepeat("Enter location-from coordinate Z: ", RouteValidator.validateFromZ, NumParser.longParser);
    }

    public String readFromName() {
        return inputRepeat("Enter location-from name: ", RouteValidator.validateFromName);
    }

    public Double readToX() {
        return (Double) inputRepeat("Enter location-to coordinate X: ", RouteValidator.validateToX, NumParser.doubleParser);
    }

    public Integer readToY() {
        return (Integer) inputRepeat("Enter location-to coordinate Y: ", RouteValidator.validateToY, NumParser.integerParser);
    }

    public String readToName() {
        return inputRepeat("Enter location-to name: ", RouteValidator.validateToName);
    }

    public Long readDistance() {
        return (Long) inputRepeat("Enter distance: ", RouteValidator.validateDistance, NumParser.longParser);
    }

    public String readSelect() {
        return inputRepeat("Are you sure (collection will not be saved)? (YES / NO) ", SelectValidator.validateSelect);
    }

    public String readLocalData() {
        return inputRepeat("Do you want to return previous progress? (YES / NO) ", SelectValidator.validateSelect);
    }
}
