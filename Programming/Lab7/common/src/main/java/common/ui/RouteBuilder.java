package common.ui;

import common.collection.Route;
import common.collection.Coordinates;
import common.collection.LocationFrom;
import common.collection.LocationTo;

import common.parsers.DateParser;
import common.parsers.NumParser;
import common.validators.RouteValidator;

import java.io.Serializable;
import java.time.LocalDate;

public class RouteBuilder implements Serializable {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private LocationFrom from;
    private LocationTo to;
    private Long distance;

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

    public Integer getId() {
        return this.id;
    }

    public void setName(String name) {
        try {
            RouteValidator.validateName.validate(name);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCoordinates(Long coordinateX, Double coordinateY) {
        this.coordinates = new Coordinates(coordinateX, coordinateY);
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

    public void setFrom(LocationFrom from) {
        this.from = from;
    }

    public void setFrom(Integer fromX, Integer fromY, Long fromZ, String fromName) {
        this.from = new LocationFrom(fromX, fromY, fromZ, fromName);
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

    public void setTo(LocationTo to) {
        this.to = to;
    }

    public void setTo(Double toX, Integer toY, String toName) {
        this.to = new LocationTo(toX, toY, toName);
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

    public void setDistance(Long distance) {
        this.distance = distance;
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
