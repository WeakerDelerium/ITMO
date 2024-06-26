package server.ui;

import common.parsers.Parser;
import common.parsers.NumParser;
import common.validators.SelectValidator;
import common.validators.Validator;
import common.validators.RouteValidator;

public class UserInput {
    private String inputRepeat(String message, Validator validator, Boolean newLineFlag) {
        Console console = Console.getInstance();

        console.print("> " + message);
        String value = console.input().trim();

        try {
            validator.validate(value);
        } catch (IllegalArgumentException e) {
            console.printError(e.getMessage());
            return inputRepeat(message, validator, newLineFlag);
        }

        if (newLineFlag) console.println();
        return value;
    }

    private Object inputRepeat(String message, Validator validator, Parser parser, Boolean newLineFlag) {
        Console console = Console.getInstance();

        console.print("> " + message);
        String value = console.input().trim();

        try {
            validator.validate(value);
        } catch (IllegalArgumentException e) {
            console.printError(e.getMessage());
            return inputRepeat(message, validator, parser, newLineFlag);
        }

        if (newLineFlag) console.println();
        return parser.parse(value);
    }

    public String read(String message, Validator validator) {
        return inputRepeat(message, validator, true);
    }

    public Object read(String message, Validator validator, Parser parser) {
        return inputRepeat(message, validator, parser, true);
    }

    public String readName() {
        return inputRepeat("Enter name: ", RouteValidator.validateName, true);
    }

    public Long readCoordinateX() {
        return (Long) inputRepeat("Enter coordinate X: ", RouteValidator.validateCoordinateX, NumParser.longParser, true);
    }

    public Double readCoordinateY() {
        return (Double) inputRepeat("Enter coordinate Y: ", RouteValidator.validateCoordinateY, NumParser.doubleParser, true);
    }

    public Integer readFromX() {
        return (Integer) inputRepeat("Enter location-from coordinate X: ", RouteValidator.validateFromX, NumParser.integerParser, true);
    }

    public Integer readFromY() {
        return (Integer) inputRepeat("Enter location-from coordinate Y: ", RouteValidator.validateFromY, NumParser.integerParser, true);
    }

    public Long readFromZ() {
        return (Long) inputRepeat("Enter location-from coordinate Z: ", RouteValidator.validateFromZ, NumParser.longParser,true);
    }

    public String readFromName() {
        return inputRepeat("Enter location-from name: ", RouteValidator.validateFromName, true);
    }

    public Double readToX() {
        return (Double) inputRepeat("Enter location-to coordinate X: ", RouteValidator.validateToX, NumParser.doubleParser, true);
    }

    public Integer readToY() {
        return (Integer) inputRepeat("Enter location-to coordinate Y: ", RouteValidator.validateToY, NumParser.integerParser, true);
    }

    public String readToName() {
        return inputRepeat("Enter location-to name: ", RouteValidator.validateToName, true);
    }

    public Long readDistance() {
        return (Long) inputRepeat("Enter distance: ", RouteValidator.validateDistance, NumParser.longParser, true);
    }

    public String readExitSelect() {
        return inputRepeat("Are you sure? (Y / N) ", SelectValidator.validateSelect, false);
    }
}
