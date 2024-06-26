package managers;

import collection.Route;
import collection.Coordinates;
import collection.LocationFrom;
import collection.LocationTo;

import parsers.DateParser;
import parsers.NumParser;
import validators.RouteValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RouteManager {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private LocationFrom from;
    private LocationTo to;
    private Long distance;

    private final UserInputManager userInputManager;

    public RouteManager(UserInputManager userInputManager) {
        this.userInputManager = userInputManager;
    }

    public void askName() {
        this.name = this.userInputManager.readName();
    }

    public void askCoordinates() {
        this.coordinates = new Coordinates(
                this.userInputManager.readCoordinateX(),
                this.userInputManager.readCoordinateY()
        );
    }

    public void askFrom() {
        this.from = new LocationFrom(
                this.userInputManager.readFromX(),
                this.userInputManager.readFromY(),
                this.userInputManager.readFromZ(),
                this.userInputManager.readFromName()
        );
    }

    public void askTo() {
        this.to = new LocationTo(
                this.userInputManager.readToX(),
                this.userInputManager.readToY(),
                this.userInputManager.readToName()
        );
    }

    public void askDistance() {
        this.distance = this.userInputManager.readDistance();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(String id) {
        try {
            RouteValidator.validateId.validate(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
        this.id = (Integer) NumParser.integerParser.parse(id);
    }

    public void setName(String name) {
        try {
            RouteValidator.validateName.validate(name);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public void setCoordinates(String coordinateX, String coordinateY) {
        try {
            RouteValidator.validateCoordinateX.validate(coordinateX);
            RouteValidator.validateCoordinateY.validate(coordinateY);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
        this.coordinates = new Coordinates(
                (Long) NumParser.longParser.parse(coordinateX),
                (Double) NumParser.doubleParser.parse(coordinateY)
        );
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreationDate(String creationDate) {
        try {
            RouteValidator.validateCreationDate.validate(creationDate);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
        this.creationDate = (LocalDate) DateParser.dateParser.parse(creationDate);
    }

    public void setFrom(String fromX, String fromY, String fromZ, String fromName) {
        try {
            RouteValidator.validateFromX.validate(fromX);
            RouteValidator.validateFromY.validate(fromY);
            RouteValidator.validateFromZ.validate(fromZ);
            RouteValidator.validateFromName.validate(fromName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
        this.from = new LocationFrom(
                (Integer) NumParser.integerParser.parse(fromX),
                (Integer) NumParser.integerParser.parse(fromX),
                (Long) NumParser.longParser.parse(fromX),
                fromName
        );
    }

    public void setTo(String toX, String toY, String toName) {
        try {
            RouteValidator.validateToX.validate(toX);
            RouteValidator.validateToY.validate(toY);
            RouteValidator.validateToName.validate(toName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
        this.to = new LocationTo(
                (Double) NumParser.doubleParser.parse(toX),
                (Integer) NumParser.integerParser.parse(toY),
                toName
        );
    }

    public void setDistance(String distance) {
        try {
            RouteValidator.validateDistance.validate(distance);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
        this.distance = (Long) NumParser.longParser.parse(distance);
    }

    public Route getRoute() {
        return new Route(this.id, this.name, this.coordinates, this.creationDate, this.from, this.to, this.distance);
    }
}
