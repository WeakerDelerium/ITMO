package client;

import client.managers.ScreenCastHelper;
import client.managers.User;

import common.util.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private static ScreenCastHelper.Display initialDisplay = ScreenCastHelper.Display.START;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(initialDisplay == ScreenCastHelper.Display.START ? "/fxml/start.fxml" : "/fxml/death.fxml"));
        ScrollPane loader = fxmlLoader.load();

        Scene scene = new Scene(loader);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/scroll.css")).toExternalForm());

        Font.loadFont(getClass().getResourceAsStream("/fonts/RuslanDisplay.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Oxanium.ttf"), 12);

        ScreenCastHelper.getInstance().setScreenState(initialDisplay);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) stage.close();
        });

        stage.setTitle("my prog");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Press ESC to exit");
        stage.setFullScreenExitKeyCombination(null);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        try {
            User.getInstance().connect("localhost", Constants.AVAILABLE_PORT);
        } catch (IOException e) {
            initialDisplay = ScreenCastHelper.Display.DEATH;
        }

        launch();
    }
}
