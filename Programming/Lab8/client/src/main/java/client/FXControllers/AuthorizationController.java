package client.FXControllers;

import client.exec.ModifiedLocaleException;
import client.managers.AuthorizationManager;
import client.managers.LocaleManager;
import client.managers.ScreenCastHelper;

import common.transfer.TagCarrier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class AuthorizationController {
    @FXML
    private AnchorPane authorizationScreen;

    @FXML
    private Button returnBtn;
    @FXML
    private Button langBtn;

    @FXML
    private Label heading;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label usernameErr;
    @FXML
    private TextField usernameInput;

    @FXML
    private Label passwdLabel;
    @FXML
    private Label passwdErr;
    @FXML
    private PasswordField passwdInput;

    @FXML
    private Label console;
    private String consolePrefix;

    @FXML
    private Button authorizeBtn;

    @FXML
    public void initialize() {
        authorizationScreen.getStyleClass().add("authorization-bg");

        returnBtn.getStyleClass().add("icon-return-btn");
        langBtn.getStyleClass().add("icon-lang-btn");

        heading.getStyleClass().addAll("heading-based", "text-wp-gradient");

        usernameErr.getStyleClass().addAll("heading-based", "error");
        usernameInput.getStyleClass().addAll("txt-based", "input-based");

        passwdErr.getStyleClass().addAll("heading-based", "error");
        passwdInput.getStyleClass().addAll("txt-based", "input-based");

        console.getStyleClass().addAll("txt-based", "error");

        authorizeBtn.getStyleClass().addAll("txt-based", "btn-based", "authorization-btn");
    }

    @FXML
    private void goStartScreen() throws IOException {
        ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.START, authorizationScreen);
    }

    @FXML
    private void goLangScreen() throws IOException {
        ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.LANG, authorizationScreen);
    }

    private void goMainScreen() throws IOException {
        ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.MAIN, authorizationScreen);
    }

    @FXML
    private void authorize() throws IOException {
        hideErrors();
        try {
            String username = usernameInput.getText();
            String passwd = passwdInput.getText();
            switch (AuthorizationManager.authorize(ScreenCastHelper.getInstance().getScreenState(), username, passwd)) {
                case USER_NOT_FOUND -> handleError(usernameErr, getError("userNotFound", username));
                case ALREADY_REGISTER -> handleError(usernameErr, getError("userExists", username));
                case WRONG_PASSWD -> handleError(passwdErr, getError("wrongPassword"));
                case WRONG_USERNAME_FORMAT -> handleError(usernameErr, getError("usernameFormat"));
                case WRONG_PASSWD_FORMAT -> handleError(passwdErr, getError("passwordFormat"));
                case NOT_AVAILABLE -> handleError(null, getError("authorizationNotAvailable"));
                default -> goMainScreen();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.DEATH, authorizationScreen);
        }
    }

    private ModifiedLocaleException getError(String tag) {
        return new ModifiedLocaleException(new TagCarrier(tag, null));
    }

    private ModifiedLocaleException getError(String tag, Object data) {
        return new ModifiedLocaleException(new TagCarrier(tag, data));
    }

    private void hideErrors() {
        usernameErr.setText("");
        passwdErr.setText("");
        console.setText("");
    }

    private void handleError(Label labelErr, ModifiedLocaleException exception) {
        if (labelErr != null) labelErr.setText("*");
        console.setText(consolePrefix + ": " + exception.getLocalizedMessage());
    }

    public void generateAccordingToLocale(ScreenCastHelper.Display type) {
        Locale locale = LocaleManager.getInstance().getCurrentLocale();

        ResourceBundle officialBundle = ResourceBundle.getBundle("client.locale.Official", locale);
        ResourceBundle headingBundle = ResourceBundle.getBundle("client.locale.Heading", locale);
        ResourceBundle labelBundle = ResourceBundle.getBundle("client.locale.Label", locale);
        ResourceBundle placeholderBundle = ResourceBundle.getBundle("client.locale.Placeholder", locale);
        ResourceBundle buttonBundle = ResourceBundle.getBundle("client.locale.Button", locale);

        usernameLabel.setText(labelBundle.getString("username"));
        passwdLabel.setText(labelBundle.getString("passwd"));

        if (type == ScreenCastHelper.Display.LOG_IN) generateLogIn(headingBundle, placeholderBundle, buttonBundle);
        else generateSignUp(headingBundle, placeholderBundle, buttonBundle);

        consolePrefix = officialBundle.getString("error");
    }

    private void generateLogIn(ResourceBundle hb, ResourceBundle pb, ResourceBundle bb) {
        heading.setText(hb.getString("logIn"));
        heading.setLayoutY(heading.getLayoutY() + 35);

        usernameInput.setPromptText(pb.getString("logInUsername"));
        passwdInput.setPromptText(pb.getString("logInPasswd"));

        String btnText = bb.getString("logIn");
        authorizeBtn.setText(btnText.substring(0, 1).toUpperCase() + btnText.substring(1));
    }

    private void generateSignUp(ResourceBundle hb, ResourceBundle pb, ResourceBundle bb) {
        heading.setText(hb.getString("signUp"));
        heading.setLayoutY(heading.getLayoutY() - 20);

        usernameInput.setPromptText(pb.getString("signUpUsername"));
        passwdInput.setPromptText(pb.getString("signUpPasswd"));

        String btnText = bb.getString("signUp");
        authorizeBtn.setText(btnText.substring(0, 1).toUpperCase() + btnText.substring(1));
    }
}
