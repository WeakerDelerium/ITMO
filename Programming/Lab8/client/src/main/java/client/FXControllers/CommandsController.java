package client.FXControllers;

import client.commands.*;
import client.exec.ModifiedLocaleException;
import client.managers.LocaleManager;
import client.managers.ScreenCastHelper;

import client.validators.ScriptValidator;
import common.transfer.TagCarrier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CommandsController {
    @FXML
    private AnchorPane commandsScreen;

    @FXML
    private Button returnBtn;

    @FXML
    private Label heading;

    @FXML
    private Button clearBtn;
    @FXML
    private Button createIfMinBtn;
    @FXML
    private Button removeByIdBtn;
    @FXML
    private Button removeGreaterBtn;
    @FXML
    private Button executeScriptBtn;

    private String chooseWindowLabel;

    @FXML
    private Label removeByIdLabel;

    @FXML
    private Label idErr;
    @FXML
    private TextField idInput;

    @FXML
    private Label console;
    private String consolePrefix;

    private final ClearCommand clearCommand = new ClearCommand();
    private final RemoveByIdCommand removeByIdCommand = new RemoveByIdCommand();
    private final ExecuteScriptCommand executeScriptCommand = new ExecuteScriptCommand();

    @FXML
    private void initialize() {
        commandsScreen.getStyleClass().add("commands-bg");

        returnBtn.getStyleClass().add("icon-return-btn");

        heading.getStyleClass().addAll("heading-based", "text-wp-gradient");

        for (Button btn: new Button[]{clearBtn, createIfMinBtn, removeByIdBtn, removeGreaterBtn, executeScriptBtn})
            btn.getStyleClass().addAll("txt-based", "btn-based");

        removeByIdBtn.setDisable(true);
        idErr.getStyleClass().addAll("heading-based", "error");
        idInput.getStyleClass().addAll("txt-based", "input-based");
        idInput.textProperty().addListener(((observable, oldValue, newValue) -> removeByIdBtn.setDisable(newValue.trim().isEmpty())));

        console.getStyleClass().addAll("txt-based", "error");

        generateAccordingToLocale();
    }

    @FXML
    private void goMainScreen() throws IOException {
        ScreenCastHelper.getInstance().generatePrevious(commandsScreen);
    }

    private void goDeathScreen() {
        try {
            ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.DEATH, commandsScreen);
        } catch (IOException ignored) {}
    }

    @FXML
    private void clearHandle() throws Exception {
        ScreenCastHelper.getInstance().generatePrevious(commandsScreen, clearCommand.submit(new String[]{}));
    }

    @FXML
    private void addIfMinHandle() throws IOException {
        ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.MIN_CREATION_FORM, commandsScreen);
    }

    @FXML
    private void removeByIdHandle() throws Exception {
        try {
            ScreenCastHelper.getInstance().generatePrevious(commandsScreen, removeByIdCommand.submit(new String[]{idInput.getText()}));
        } catch (ModifiedLocaleException e) {
            idErr.setText("*");
            generateConsoleErr(e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
            idErr.setText("*");
            ModifiedLocaleException exception = getError(e.getMessage());
            generateConsoleErr(exception.getLocalizedMessage());
        } catch (IOException e) {
            goDeathScreen();
        }
    }

    @FXML
    private void removeGreaterHandle() throws IOException {
        ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.GREATER_REMOVING_FORM, commandsScreen);
    }

    @FXML
    private void executeScriptHandle() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(chooseWindowLabel);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Stage stage = (Stage) executeScriptBtn.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                ScriptValidator.scriptHistory.clear();
                ScreenCastHelper.getInstance().generatePrevious(commandsScreen, executeScriptCommand.submit(new String[]{selectedFile.getAbsolutePath()}));
            } catch (ModifiedLocaleException e) {
                generateConsoleErr(e.getLocalizedMessage());
            } catch (IllegalArgumentException e) {
                ModifiedLocaleException exception = getError(e.getMessage());
                generateConsoleErr(exception.getLocalizedMessage());
            } catch (IOException e) {
                goDeathScreen();
            }
        } else {
            ModifiedLocaleException exception = getError("fileNotSelected");
            generateConsoleErr(exception.getLocalizedMessage());
        }
    }

    private ModifiedLocaleException getError(String tag) {
        return new ModifiedLocaleException(new TagCarrier(tag, null));
    }

    private void generateConsoleErr(String message) {
        console.setText(consolePrefix + ": " + message);
    }

    private void generateAccordingToLocale() {
        Locale locale = LocaleManager.getInstance().getCurrentLocale();

        ResourceBundle officialBundle = ResourceBundle.getBundle("client.locale.Official", locale);
        ResourceBundle headingBundle = ResourceBundle.getBundle("client.locale.Heading", locale);
        ResourceBundle labelBundle = ResourceBundle.getBundle("client.locale.Label", locale);
        ResourceBundle placeholderBundle = ResourceBundle.getBundle("client.locale.Placeholder", locale);
        ResourceBundle buttonBundle = ResourceBundle.getBundle("client.locale.Button", locale);

        heading.setText(headingBundle.getString("commands"));

        removeByIdLabel.setText(labelBundle.getString("removeById"));

        idInput.setPromptText(placeholderBundle.getString("id"));

        clearBtn.setText(buttonBundle.getString("clear"));
        createIfMinBtn.setText(buttonBundle.getString("createIfMin"));
        removeByIdBtn.setText(buttonBundle.getString("remove"));
        removeGreaterBtn.setText(buttonBundle.getString("removeGreater"));
        executeScriptBtn.setText(buttonBundle.getString("script"));

        consolePrefix = officialBundle.getString("info");

        chooseWindowLabel = officialBundle.getString("script");
    }
}
