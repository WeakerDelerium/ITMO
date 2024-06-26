package common.ui;

import common.parsers.Parser;
import common.parsers.NumParser;
import common.validators.SelectValidator;
import common.validators.Validator;
import common.validators.RouteValidator;

public class UserInput {
    private String inputRepeat(String message, Validator validator) {
        Console console = Console.getInstance();

        console.print("> " + message);
        String value = console.input().trim();

        try {
            validator.validate(value);
        } catch (IllegalArgumentException e) {
            console.printError(e.getMessage());
            return inputRepeat(message, validator);
        }

        console.println();
        return value;
    }

    private Object inputRepeat(String message, Validator validator, Parser parser) {
        Console console = Console.getInstance();

        console.print("> " + message);
        String value = console.input().trim();

        try {
            validator.validate(value);
        } catch (IllegalArgumentException e) {
            console.printError(e.getMessage());
            return inputRepeat(message, validator, parser);
        }

        console.println();
        return parser.parse(value);
    }

    public String read(String message, Validator validator) {
        return inputRepeat(message, validator);
    }

    public Object read(String message, Validator validator, Parser parser) {
        return inputRepeat(message, validator, parser);
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
        return inputRepeat("Are you sure? (Y / N) ", SelectValidator.validateSelect);
    }
}
