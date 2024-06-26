package client.managers;

import client.FXControllers.AuthorizationController;
import client.FXControllers.FormController;
import client.FXControllers.MainController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Stack;

public class ScreenCastHelper {
    private static ScreenCastHelper helper = null;

    private Display current;
    private final Stack<Display> layers;

    private ScreenCastHelper() {
        this.layers = new Stack<>();
    }

    public enum Display {
        START,
        LANG,
        LOG_IN,
        SIGN_UP,
        MAIN,
        CREATION_FORM,
        UPDATE_FORM,
        MIN_CREATION_FORM,
        GREATER_REMOVING_FORM,
        COMMANDS,
        VISUALIZE,
        DEATH
    }

    public void setAndGenerate(Display display, AnchorPane screen) throws IOException {
        setScreenState(display);
        generate(screen);
    }

    public void setAndGenerate(Display display, AnchorPane screen, Object data) throws IOException {
        setScreenState(display);
        generate(screen, data);
    }

    private void generate(AnchorPane screen) throws IOException {
        switch (this.current) {
            case START -> show(screen, "/fxml/start.fxml");
            case LOG_IN, SIGN_UP -> showAuthorization(screen);
            case LANG -> show(screen, "/fxml/lang.fxml");
            case MAIN -> show(screen, "/fxml/main.fxml");
            case CREATION_FORM, MIN_CREATION_FORM, GREATER_REMOVING_FORM -> showForm(screen);
            case COMMANDS -> show(screen, "/fxml/commands.fxml");
            case VISUALIZE -> show(screen, "/fxml/visualize.fxml");
            case DEATH -> show(screen, "/fxml/death.fxml");
        }
    }

    private void generate(AnchorPane screen, Object data) throws IOException {
        switch (this.current) {
            case MAIN -> showMainWithConsole(screen, data);
            case UPDATE_FORM -> showUpdateForm(screen, data);
            default -> generate(screen);
        }
    }

    public void generatePrevious(AnchorPane screen) throws IOException {
        this.current = toPreviousScreenState();
        generate(screen);
    }

    public void generatePrevious(AnchorPane screen, Object data) throws IOException {
        this.current = toPreviousScreenState();
        generate(screen, data);
    }

    private void show(AnchorPane screen, String filename) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        ScrollPane wrapper = loader.load();
        AnchorPane newScreen = (AnchorPane) wrapper.getContent();

        setScreenContent(screen, newScreen);
    }

    private void showAuthorization(AnchorPane screen) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/authorization.fxml"));
        ScrollPane wrapper = loader.load();
        AnchorPane newScreen = (AnchorPane) wrapper.getContent();

        AuthorizationController controller = loader.getController();
        controller.generateAccordingToLocale(this.current);

        setScreenContent(screen, newScreen);
    }

    private void showMainWithConsole(AnchorPane screen, Object data) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        ScrollPane wrapper = loader.load();
        AnchorPane newScreen = (AnchorPane) wrapper.getContent();

        MainController controller = loader.getController();
        controller.generateConsoleMessage((String) data);

        setScreenContent(screen, newScreen);
    }

    private void showForm(AnchorPane screen) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form.fxml"));
        ScrollPane wrapper = loader.load();
        AnchorPane newScreen = (AnchorPane) wrapper.getContent();

        FormController controller = loader.getController();
        controller.generateAccordingToLocale(this.current);

        setScreenContent(screen, newScreen);
    }

    private void showUpdateForm(AnchorPane screen, Object data) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form.fxml"));
        ScrollPane wrapper = loader.load();
        AnchorPane newScreen = (AnchorPane) wrapper.getContent();

        FormController controller = loader.getController();
        controller.generateAccordingToLocale(this.current);
        controller.generateUpdate((Integer) data);

        setScreenContent(screen, newScreen);
    }

    private void setScreenContent(AnchorPane screen, AnchorPane newScreen) {
        screen.getChildren().setAll(newScreen);
        screen.setPrefSize(newScreen.getPrefHeight() > 1080 ? newScreen.getPrefWidth() - 10 : newScreen.getPrefWidth(), newScreen.getPrefHeight());

        ScrollPane screenWrapper = getScreenWrapper(screen);
        screenWrapper.setContent(screen);
    }

    public void setScreenState(Display display) {
        this.current = display;
        if (this.layers.contains(display)) this.layers.clear();
        this.layers.push(display);
    }

    public Display getScreenState()  {
        return this.current;
    }

    private Display toPreviousScreenState() {
        this.layers.pop();
        return this.layers.peek();
    }

    private ScrollPane getScreenWrapper(AnchorPane screen) {
        Parent screenParent = screen.getParent();
        while (!(screenParent instanceof ScrollPane)) screenParent = screenParent.getParent();
        return (ScrollPane) screenParent;
    }

    public static ScreenCastHelper getInstance() {
        if (helper == null) helper = new ScreenCastHelper();
        return helper;
    }
}
