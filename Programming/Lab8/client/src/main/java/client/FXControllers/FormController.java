package client.FXControllers;

import client.commands.AddCommand;
import client.commands.AddIfMinCommand;
import client.commands.RemoveGreaterCommand;
import client.commands.UpdateCommand;
import client.exec.ModifiedLocaleException;
import client.managers.CollectionManager;
import client.managers.LocaleManager;
import client.managers.ScreenCastHelper;

import common.collection.Route;
import common.transfer.TagCarrier;
import common.ui.RouteBuilder;
import common.validators.RouteValidator;
import common.validators.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class FormController {
    @FXML
    private AnchorPane formScreen;

    @FXML
    private Button returnBtn;

    @FXML
    private Label heading;

    @FXML
    private Label cordHeading;
    @FXML
    private Label fromHeading;
    @FXML
    private Label toHeading;

    @FXML
    private Label nameLabel;
    @FXML
    private Label nameErr;
    @FXML
    private TextField nameInput;

    @FXML
    private Label coordinateXLabel;
    @FXML
    private Label coordinateXErr;
    @FXML
    private TextField coordinateXInput;

    @FXML
    private Label coordinateYLabel;
    @FXML
    private Label coordinateYErr;
    @FXML
    private TextField coordinateYInput;

    @FXML
    private Label fromXLabel;
    @FXML
    private Label fromXErr;
    @FXML
    private TextField fromXInput;

    @FXML
    private Label fromYLabel;
    @FXML
    private Label fromYErr;
    @FXML
    private TextField fromYInput;

    @FXML
    private Label fromZLabel;
    @FXML
    private Label fromZErr;
    @FXML
    private TextField fromZInput;

    @FXML
    private Label fromNameLabel;
    @FXML
    private Label fromNameErr;
    @FXML
    private TextField fromNameInput;

    @FXML
    private Label toXLabel;
    @FXML
    private Label toXErr;
    @FXML
    private TextField toXInput;

    @FXML
    private Label toYLabel;
    @FXML
    private Label toYErr;
    @FXML
    private TextField toYInput;

    @FXML
    private Label toNameLabel;
    @FXML
    private Label toNameErr;
    @FXML
    private TextField toNameInput;

    @FXML
    private Label distanceLabel;
    @FXML
    private Label distanceErr;
    @FXML
    private TextField distanceInput;

    @FXML
    private Button cancelBtn;
    @FXML
    private Button actionBtn;

    @FXML
    private Label consoleErr;

    private ScreenCastHelper.Display formType;

    private Route suggestedRoute;

    private final AddCommand addCommand = new AddCommand();
    private final UpdateCommand updateCommand = new UpdateCommand();
    private final AddIfMinCommand addIfMinCommand = new AddIfMinCommand();
    private final RemoveGreaterCommand removeGreaterCommand = new RemoveGreaterCommand();

    @FXML
    private void initialize() {
        formScreen.getStyleClass().add("form-bg");

        returnBtn.getStyleClass().add("icon-return-btn");

        heading.getStyleClass().addAll("heading-based", "text-wp-gradient");

        for (Label txtHeading: new Label[]{cordHeading, fromHeading, toHeading})
            txtHeading.getStyleClass().addAll("txt-based", "txt-upper", "text-wp-gradient");

        for (Label errInput: new Label[]{nameErr, coordinateXErr, coordinateYErr, fromXErr, fromYErr, fromZErr, fromNameErr, toXErr, toYErr, toNameErr, distanceErr})
            errInput.getStyleClass().addAll("heading-based", "error");

        for (TextField input: new TextField[]{nameInput, coordinateXInput, coordinateYInput, fromXInput, fromYInput, fromZInput, fromNameInput, toXInput, toYInput, toNameInput, distanceInput})
            input.getStyleClass().addAll("txt-based", "input-based");

        cancelBtn.getStyleClass().addAll("txt-based", "btn-based");
        actionBtn.getStyleClass().addAll("txt-based", "btn-based");

        consoleErr.getStyleClass().addAll("txt-small", "error");
    }

    @FXML
    private void goMainScreen() throws IOException {
        ScreenCastHelper.getInstance().generatePrevious(formScreen);
    }

    @FXML
    private void doFormAction() throws IOException {
        hideErrors();
        if (check()) {
            try {
                ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.MAIN, formScreen, getCommandResult());
            } catch (Exception e) {
                e.printStackTrace();
                ScreenCastHelper.getInstance().generatePrevious(formScreen, e.getMessage());
            }
        }
        else setLayouts();
    }

    private String getCommandResult() throws Exception {
        return switch (this.formType) {
            case CREATION_FORM -> addCommand.submit(new String[]{}, parseRouteBuilder());
            case UPDATE_FORM -> updateCommand.submit(new String[]{String.valueOf(suggestedRoute.id())}, parseRouteBuilder());
            case MIN_CREATION_FORM -> addIfMinCommand.submit(new String[]{}, parseRouteBuilder());
            default -> removeGreaterCommand.submit(new String[]{}, parseRouteBuilder());
        };
    }

    private void setLayouts() {
        int consoleErrLines = consoleErr.getText().split("\n").length;
        cancelBtn.setLayoutY(2531 + 31 * consoleErrLines);
        actionBtn.setLayoutY(2531 + 31 * consoleErrLines);
    }

    private void hideErrors() {
        for (Label err : new Label[]{consoleErr, nameErr, coordinateXErr, coordinateYErr, fromXErr, fromYErr, fromZErr, fromNameErr, toXErr, toYErr, toNameErr, distanceErr}) err.setText("");
    }

    private boolean check() {
        LinkedHashMap<TextField, Validator> fieldValidator = getValidatorMap();
        HashMap<TextField, Label> errorLabels = getErrorLabelMap();

        for (TextField input: fieldValidator.keySet()) {
            try {
                fieldValidator.get(input).validate(input.getText());
            } catch (IllegalArgumentException e) {
                errorLabels.get(input).setText("*");

                ModifiedLocaleException exception = new ModifiedLocaleException(new TagCarrier(e.getMessage(), null));
                String message = exception.getLocalizedMessage();

                if (consoleErr.getText().isEmpty()) consoleErr.setText("ERROR: " + message);
                else consoleErr.setText(consoleErr.getText() + "\nERROR: " + message);
            }
        }

        return consoleErr.getText().isEmpty();
    }

    private LinkedHashMap<TextField, Validator> getValidatorMap() {
        LinkedHashMap<TextField, Validator> validatorMap = new LinkedHashMap<>();

        validatorMap.put(nameInput, RouteValidator.validateName);
        validatorMap.put(coordinateXInput, RouteValidator.validateCoordinateX);
        validatorMap.put(coordinateYInput, RouteValidator.validateCoordinateY);
        validatorMap.put(fromXInput, RouteValidator.validateFromX);
        validatorMap.put(fromYInput, RouteValidator.validateFromY);
        validatorMap.put(fromZInput, RouteValidator.validateFromZ);
        validatorMap.put(fromNameInput, RouteValidator.validateFromName);
        validatorMap.put(toXInput, RouteValidator.validateToX);
        validatorMap.put(toYInput, RouteValidator.validateToY);
        validatorMap.put(toNameInput, RouteValidator.validateToName);
        validatorMap.put(distanceInput, RouteValidator.validateDistance);

        return validatorMap;
    }

    private HashMap<TextField, Label> getErrorLabelMap() {
        HashMap<TextField, Label> errorLabelMap = new HashMap<>();

        errorLabelMap.put(nameInput, nameErr);
        errorLabelMap.put(coordinateXInput, coordinateXErr);
        errorLabelMap.put(coordinateYInput, coordinateYErr);
        errorLabelMap.put(fromXInput, fromXErr);
        errorLabelMap.put(fromYInput, fromYErr);
        errorLabelMap.put(fromZInput, fromZErr);
        errorLabelMap.put(fromNameInput, fromNameErr);
        errorLabelMap.put(toXInput, toXErr);
        errorLabelMap.put(toYInput, toYErr);
        errorLabelMap.put(toNameInput, toNameErr);
        errorLabelMap.put(distanceInput, distanceErr);

        return errorLabelMap;
    }

    private RouteBuilder parseRouteBuilder() {
        RouteBuilder routeBuilder = new RouteBuilder();

        routeBuilder.setName(nameInput.getText());
        routeBuilder.setCoordinates(coordinateXInput.getText(), coordinateYInput.getText());
        routeBuilder.setFrom(fromXInput.getText(), fromYInput.getText(), fromZInput.getText(), fromNameInput.getText());
        routeBuilder.setTo(toXInput.getText(), toYInput.getText(), toNameInput.getText());
        routeBuilder.setDistance(distanceInput.getText());

        return routeBuilder;
    }

    public void generateAccordingToLocale(ScreenCastHelper.Display type) {
        Locale locale = LocaleManager.getInstance().getCurrentLocale();

        ResourceBundle headingBundle = ResourceBundle.getBundle("client.locale.Heading", locale);
        ResourceBundle labelBundle = ResourceBundle.getBundle("client.locale.Label", locale);
        ResourceBundle placeholderBundle = ResourceBundle.getBundle("client.locale.Placeholder", locale);
        ResourceBundle buttonBundle = ResourceBundle.getBundle("client.locale.Button", locale);

        nameLabel.setText(labelBundle.getString("name"));
        fromNameLabel.setText(labelBundle.getString("name"));
        toNameLabel.setText(labelBundle.getString("name"));
        distanceLabel.setText(labelBundle.getString("distance"));

        nameInput.setPromptText(placeholderBundle.getString("name"));
        coordinateXInput.setPromptText(placeholderBundle.getString("x"));
        coordinateYInput.setPromptText(placeholderBundle.getString("y"));
        fromXInput.setPromptText(placeholderBundle.getString("x"));
        fromYInput.setPromptText(placeholderBundle.getString("y"));
        fromZInput.setPromptText(placeholderBundle.getString("z"));
        fromNameInput.setPromptText(placeholderBundle.getString("name"));
        toXInput.setPromptText(placeholderBundle.getString("x"));
        toYInput.setPromptText(placeholderBundle.getString("y"));
        toNameInput.setPromptText(placeholderBundle.getString("name"));
        distanceInput.setPromptText(placeholderBundle.getString("distance"));

        cancelBtn.setText(buttonBundle.getString("cancel"));

        switch (type) {
            case CREATION_FORM -> generateFormDeterminants("create", headingBundle, buttonBundle);
            case UPDATE_FORM -> generateFormDeterminants("update", headingBundle, buttonBundle);
            case MIN_CREATION_FORM -> generateFormDeterminants("createIfMin", headingBundle, buttonBundle);
            case GREATER_REMOVING_FORM -> generateFormDeterminants("removeGreater", headingBundle, buttonBundle);
        }

        this.formType = type;
    }

    public void generateFormDeterminants(String task, ResourceBundle hb, ResourceBundle bb) {
        heading.setText(hb.getString(task));
        actionBtn.setText(bb.getString(task));
    }

    public void generateUpdate(Integer id) {
        suggestedRoute = CollectionManager.getInstance().getRouteById(id);

        nameInput.setText(suggestedRoute.name());
        coordinateXInput.setText(String.valueOf(suggestedRoute.coordinates().x()));
        coordinateYInput.setText(String.valueOf(suggestedRoute.coordinates().y()));
        fromXInput.setText(String.valueOf(suggestedRoute.from().x()));
        fromYInput.setText(String.valueOf(suggestedRoute.from().y()));
        fromZInput.setText(String.valueOf(suggestedRoute.from().z()));
        fromNameInput.setText(suggestedRoute.from().name());
        toXInput.setText(String.valueOf(suggestedRoute.to().x()));
        toYInput.setText(String.valueOf(suggestedRoute.to().y()));
        toNameInput.setText(suggestedRoute.to().name());
        distanceInput.setText(String.valueOf(suggestedRoute.distance()));
    }
}
