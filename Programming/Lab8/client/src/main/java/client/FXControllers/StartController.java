package client.FXControllers;

import client.managers.LocaleManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import client.managers.ScreenCastHelper;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class StartController {
    @FXML
    private AnchorPane startScreen;

    @FXML
    private Button langBtn;

    @FXML
    private Label heading;

    @FXML
    private Button logInBtn;
    @FXML
    private Button signUpBtn;

    @FXML
    public void initialize() {
        startScreen.getStyleClass().add("start-bg");

        langBtn.getStyleClass().add("icon-lang-btn");

        logInBtn.getStyleClass().addAll("txt-based", "btn-based");
        signUpBtn.getStyleClass().addAll("txt-based", "btn-based");

        generateAccordingToLocale();
    }

    @FXML
    private void goLangScreen() throws IOException {
        ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.LANG, startScreen);
    }

    @FXML
    private void goLogInScreen() throws IOException {
        ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.LOG_IN, startScreen);
    }

    @FXML
    private void goSingUpScreen() throws IOException {
        ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.SIGN_UP, startScreen);
    }

    private void generateAccordingToLocale() {
        Locale locale = LocaleManager.getInstance().getCurrentLocale();

        ResourceBundle headingBundle = ResourceBundle.getBundle("client.locale.Heading", locale);
        ResourceBundle buttonBundle = ResourceBundle.getBundle("client.locale.Button", locale);

        heading.setText(headingBundle.getString("start"));

        logInBtn.setText(buttonBundle.getString("logIn"));
        signUpBtn.setText(buttonBundle.getString("signUp"));
    }
}