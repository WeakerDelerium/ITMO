package client.FXControllers;

import client.managers.CollectionManager;
import client.managers.LocaleManager;
import client.managers.ScreenCastHelper;
import client.managers.User;
import client.thread.ThreadHelper;
import client.util.UniqueColorGenerator;

import common.collection.Route;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class VisualizeController {
    @FXML
    private AnchorPane visualizeScreen;

    @FXML
    private Button returnBtn;

    @FXML
    private Label heading;

    @FXML
    private Canvas plane;
    private GraphicsContext planeGC;

    private final CollectionManager collectionManager = CollectionManager.getInstance();

    private ScheduledExecutorService scheduler;

    private static TreeSet<Route> currentCollection = new TreeSet<>();
    private static HashMap<String, HashSet<Integer>> usersAvailability = new HashMap<>();
    private static HashMap<String, Color> usersColors = new HashMap<>();

    private final ArrayList<EventHandler<MouseEvent>> bindListeners = new ArrayList<>();
    private final ArrayList<Tooltip> bindTooltips = new ArrayList<>();

    private double width;
    private double height;

    private int step;
    private double k;

    @FXML
    public void initialize() {
        visualizeScreen.getStyleClass().add("visualize-bg");

        heading.getStyleClass().addAll("heading-based", "text-wp-gradient");

        returnBtn.getStyleClass().add("icon-return-btn");

        drawCoordinatePlane();
        if (!usersColors.isEmpty()) drawCollection();

        trackCollectionUpdate();

        generateAccordingToLocale();
    }

    @FXML
    private void goMainScreen() throws IOException {
        scheduler.shutdownNow();
        bindListeners.forEach(listener -> visualizeScreen.removeEventHandler(MouseEvent.MOUSE_MOVED, listener));
        bindTooltips.forEach(tooltip -> Tooltip.uninstall(visualizeScreen, tooltip));
        ScreenCastHelper.getInstance().generatePrevious(visualizeScreen);
    }

    private void goDeathScreen() {
        try {
            scheduler.shutdownNow();
            ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.DEATH, visualizeScreen);
        } catch (IOException ignored) {
        }
    }

    private void drawCoordinatePlane() {
        width = plane.getWidth();
        height = plane.getHeight();

        step = 25;
        k = (width / 2) / 300;

        planeGC = plane.getGraphicsContext2D();

        planeGC.clearRect(0, 0, width, height);

        planeGC.setStroke(Color.WHITE);
        planeGC.setFill(Color.WHITE);
        planeGC.setFill(Color.WHITE);

        drawCells();
        drawRange();
        drawXOY();
    }

    private void drawCells() {
        planeGC.setLineWidth(1);

        double serif = step * k;
        for (int i = 0; i <= height / step; i++) planeGC.strokeLine(0, serif * i, width, serif * i);
        for (int i = 0; i <= width / step; i++) planeGC.strokeLine(serif * i, 0, serif * i, height);
    }

    private void drawRange() {
        planeGC.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/Oxanium.ttf"), 15));

        double serif = step * k;
        for (int i = 1; i < height / serif; i++) {
            String num = String.valueOf((int) ((height / 2) / k) - step * i);
            planeGC.fillText(num, width / 2 - 15 - num.length() * 5, serif * i + 20);
        }
        for (int i = 0; i < width / serif; i++) {
            String num = String.valueOf(step * i - (int) ((width / 2) / k));
            planeGC.fillText(num, serif * i - 15 - num.length() * 5, height / 2 + 20);
        }
    }

    private void drawXOY() {
        planeGC.setLineWidth(5);

        drawArrow(0, height / 2, width, height / 2);
        drawArrow(width / 2, height, width / 2, 0);

        planeGC.strokeLine(0, height / 2, width, height / 2);
        planeGC.strokeLine(width / 2, 0, width / 2, height);
    }

    private void drawArrow(double x1, double y1, double x2, double y2) {
        planeGC.strokeLine(x1, y1, x2, y2);

        double arrowLength = 10;
        double arrowWidth = 7;

        double angle = Math.atan2(y2 - y1, x2 - x1);

        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        double x3 = x2 - arrowLength * cos + arrowWidth * sin;
        double y3 = y2 - arrowLength * sin - arrowWidth * cos;

        double x4 = x2 - arrowLength * cos - arrowWidth * sin;
        double y4 = y2 - arrowLength * sin + arrowWidth * cos;

        planeGC.strokeLine(x2, y2, x3, y3);
        planeGC.strokeLine(x2, y2, x4, y4);
    }

    private void drawCollection() {
        for (String username : usersAvailability.keySet()) {
            Color userColor = usersColors.get(username);
            usersAvailability.get(username).forEach(id -> drawRoute(username, collectionManager.getRouteById(id), userColor, collectionManager.getMaxDistance()));
        }
    }

    private void drawRoute(String username, Route route, Color color, long maxRad) {
        double radius = Math.max(((double) route.distance() / maxRad) * 50, 5.0);

        long coordinateX = route.coordinates().x();
        double coordinateY = route.coordinates().y();

        if (coordinateX > 300 || coordinateY > 150) return;

        double x = width / 2 + coordinateX * k;
        double y = height / 2 - coordinateY * k;

        Circle routeVisualize = new Circle(x + plane.getLayoutX(), y + plane.getLayoutY(), radius, color);
        bindRadiusAnimation(routeVisualize);
        bindRouteTooltip(routeVisualize, username, route);

        Platform.runLater(() -> visualizeScreen.getChildren().add(routeVisualize));
    }

    private void bindRadiusAnimation(Circle circle) {
        double radius = circle.getRadius();
        DoubleProperty radiusProperty = circle.radiusProperty();

        KeyValue maxRadius = new KeyValue(radiusProperty, 1 * radius);
        KeyValue minRadius = new KeyValue(radiusProperty, 0.85 * radius);

        Timeline radiusAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, maxRadius),
                new KeyFrame(Duration.seconds(1), minRadius),
                new KeyFrame(Duration.seconds(2), maxRadius)
        );

        radiusAnimation.setAutoReverse(true);
        radiusAnimation.setCycleCount(Timeline.INDEFINITE);

        radiusAnimation.play();
    }

    private void bindRouteTooltip(Circle circle, String username, Route route) {
        Tooltip tooltip = new Tooltip(("""
                Owner: %s
                Id: %d
                Name: %s
                X: %d
                Y: %f
                Creation date: %s
                From X: %d
                From Y: %d
                From Z: %d
                From NAME: %s
                To X: %f
                To Y: %d
                To NAME: %s
                Distance: %d"""
        ).formatted(
                username,
                route.id(),
                route.name(),
                route.coordinates().x(),
                route.coordinates().y(),
                route.creationDate(),
                route.from().x(),
                route.from().y(),
                route.from().z(),
                route.from().name(),
                route.to().x(),
                route.to().y(),
                route.to().name(),
                route.distance())
        );

        tooltip.getStyleClass().addAll("txt-based", "tooltip-based");
        tooltip.setShowDelay(Duration.INDEFINITE);

        Tooltip.install(visualizeScreen, tooltip);

        EventHandler<MouseEvent> tooltipListener = event -> {
            if (matchCircleCoords(circle, event.getX(), event.getY()))  {
                if (tooltip.isShowing()) return;
                tooltip.show(plane, event.getX(), event.getY());
                event.consume();
            } else tooltip.hide();
        };

        visualizeScreen.addEventHandler(MouseEvent.MOUSE_MOVED, tooltipListener);

        bindListeners.add(tooltipListener);
        bindTooltips.add(tooltip);
    }

    private boolean matchCircleCoords(Circle circle, double posX, double posY) {
        double x = circle.getCenterX();
        double y = circle.getCenterY();

        double radius = circle.getRadius();

        return Math.sqrt(Math.pow(x - posX, 2) + Math.pow(y - posY, 2)) <= radius;
    }

    private void trackCollectionUpdate() {
        scheduler = new ScheduledThreadPoolExecutor(1, ThreadHelper::toDaemonThread);
        scheduler.scheduleAtFixedRate(this::trackLoop, 0, 5, TimeUnit.SECONDS);
    }

    private void trackLoop() {
        try {
            if (User.getInstance().getUserInfo() == null) return;

            if (collectionManager.checkEquals(currentCollection)) return;

            currentCollection = collectionManager.getCollection();
            usersAvailability = collectionManager.getUsersAvailability();

            while (!currentCollection.isEmpty() && usersAvailability.isEmpty()) {}

            int accordanceCnt = usersAvailability.size() - usersColors.size();
            if (accordanceCnt != 0) updateUsersColors(accordanceCnt);

            Platform.runLater(() -> visualizeScreen.getChildren().removeIf(node -> node instanceof Circle));

            drawCollection();
        } catch (Exception e) {
            e.printStackTrace();
            goDeathScreen();
        }
    }

    private void updateUsersColors(int dif) {
        if (dif > 0) addToUsersColors();
        else cutUsersColors();
    }

    private void addToUsersColors() {
        for (String username : usersAvailability.keySet()) {
            if (!usersColors.containsKey(username))
                usersColors.put(username, UniqueColorGenerator.generateAnotherOneColor());
        }
    }

    private void cutUsersColors() {
        HashMap<String, Color> newUsersColor = new HashMap<>();

        for (String username : usersColors.keySet()) {
            Color userColor = usersColors.get(username);
            if (usersAvailability.containsKey(username)) newUsersColor.put(username, userColor);
            else UniqueColorGenerator.releaseColor(userColor);
        }

        usersColors = newUsersColor;
    }

    public static void reset() {
        currentCollection.clear();
        usersAvailability.clear();
        usersColors.clear();
    }

    private void generateAccordingToLocale() {
        Locale locale = LocaleManager.getInstance().getCurrentLocale();

        ResourceBundle headingBundle = ResourceBundle.getBundle("client.locale.Heading", locale);

        heading.setText(headingBundle.getString("visualize"));
    }
}
