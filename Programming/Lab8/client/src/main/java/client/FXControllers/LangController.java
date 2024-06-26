package client.FXControllers;

import client.managers.LocaleManager;
import client.managers.ScreenCastHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LangController {
    @FXML
    private AnchorPane langScreen;

    @FXML
    private Button returnBtn;

    @FXML
    private ImageView langImage;

    @FXML
    private Label heading;

    @FXML
    private Button enBtn;
    @FXML
    private Button ruBtn;
    @FXML
    private Button trBtn;
    @FXML
    private Button itBtn;
    @FXML
    private Button esBtn;

    @FXML
    public void initialize() {
        langScreen.getStyleClass().add("lang-bg");

        returnBtn.getStyleClass().add("icon-return-btn");

        langImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/lang-main.png"))));

        Button[] btns = new Button[]{enBtn, ruBtn, trBtn, itBtn, esBtn};
        for (Button btn: btns) {
            btn.getStyleClass().addAll("txt-based", "lang-btn");
            btn.setOnAction(event -> setLocale(((Button) event.getSource()).getText()));
        }

        generateAccordingToLocale();
    }

    @FXML
    private void goPreviousScreen() throws IOException {
        ScreenCastHelper.getInstance().generatePrevious(langScreen);
    }

    private void setLocale(String language) {
        LocaleManager localeManager = LocaleManager.getInstance();

        switch (language) {
            case "English" -> localeManager.setLocale("en_US");
            case "Русский" -> localeManager.setLocale("ru_RU");
            case "Türkçe" -> localeManager.setLocale("tr_TR");
            case "Italiano" -> localeManager.setLocale("it_IT");
            default -> localeManager.setLocale("es_HN");
        }

        generateAccordingToLocale();
    }

    private void generateAccordingToLocale() {
        Locale locale = LocaleManager.getInstance().getCurrentLocale();

        ResourceBundle headingBundle = ResourceBundle.getBundle("client.locale.Heading", locale);

        heading.setText(headingBundle.getString("lang"));
    }
}
