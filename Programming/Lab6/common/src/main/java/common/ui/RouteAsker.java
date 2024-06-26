package common.ui;

import common.collection.Coordinates;
import common.collection.LocationFrom;
import common.collection.LocationTo;

import java.time.LocalDate;

public class RouteAsker {
    private String name;
    private Coordinates coordinates;
    private LocationFrom from;
    private LocationTo to;
    private Long distance;

    private final UserInput userInput;

    public RouteAsker(UserInput userInput) {
        this.userInput = userInput;
    }

    public void ask() {
        askName();
        askCoordinates();
        askFrom();
        askTo();
        askDistance();
    }

    private void askName() {
        this.name = this.userInput.readName();
    }

    private void askCoordinates() {
        this.coordinates = new Coordinates(
                this.userInput.readCoordinateX(),
                this.userInput.readCoordinateY()
        );
    }

    private void askFrom() {
        this.from = new LocationFrom(
                this.userInput.readFromX(),
                this.userInput.readFromY(),
                this.userInput.readFromZ(),
                this.userInput.readFromName()
        );
    }

    private void askTo() {
        this.to = new LocationTo(
                this.userInput.readToX(),
                this.userInput.readToY(),
                this.userInput.readToName()
        );
    }

    private void askDistance() {
        this.distance = this.userInput.readDistance();
    }

    public RouteReader getRouteReader() {
        RouteReader routeReader = new RouteReader();

        routeReader.setName(this.name);
        routeReader.setCoordinates(this.coordinates);
        routeReader.setFrom(this.from);
        routeReader.setTo(this.to);
        routeReader.setDistance(this.distance);

        return routeReader;
    }
}
