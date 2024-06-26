package client.FXControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DeathController {
    @FXML
    private AnchorPane deathScreen;

    @FXML
    private Label smile;
    @FXML
    private Label error;

    @FXML
    public void initialize() {
        deathScreen.getStyleClass().add("death-bg");

        smile.getStyleClass().addAll("txt-huge", "error");
        error.getStyleClass().addAll("txt-avg", "error");
    }
}
