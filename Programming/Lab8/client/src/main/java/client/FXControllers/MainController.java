package client.FXControllers;

import client.commands.RemoveByIdCommand;
import client.commands.UpdateCommand;
import client.managers.CollectionManager;
import client.managers.LocaleManager;
import client.managers.ScreenCastHelper;
import client.managers.User;
import client.thread.CollectionUpdater;
import client.thread.ThreadHelper;

import common.collection.Route;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainController {
    @FXML
    private AnchorPane mainScreen;

    @FXML
    private Button logOutBtn;
    @FXML
    private Button langBtn;

    @FXML
    private Label username;

    @FXML
    private Label routesInfo;
    private String routesInfoPostfix;

    @FXML
    private Label filterLabel;

    @FXML
    private ComboBox<String> sortKey;
    @FXML
    private ComboBox<Object> sortValue;

    private final ObservableList<String> sortKeysList = FXCollections.observableArrayList();
    private final ObservableList<Object> sortValuesList = FXCollections.observableArrayList();

    private final ChangeListener<String> sortKeyTrigger = (observable, oldKey, newKey) -> sorting();
    private final ChangeListener<Object> sortValueTrigger = (observable, oldKey, newKey) -> filtering();

    private static String selectedColumn = "";
    private static Object selectedValue = "";

    @FXML
    private Label tableLabel;

    @FXML
    private TableView<Route> collectionView;

    private final ObservableList<Route> collectionList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Route, String> idColumn;
    @FXML
    private TableColumn<Route, String> nameColumn;
    @FXML
    private TableColumn<Route, String> coordinateXColumn;
    @FXML
    private TableColumn<Route, String> coordinateYColumn;
    @FXML
    private TableColumn<Route, String> creationDateColumn;
    @FXML
    private TableColumn<Route, String> fromXColumn;
    @FXML
    private TableColumn<Route, String> fromYColumn;
    @FXML
    private TableColumn<Route, String> fromZColumn;
    @FXML
    private TableColumn<Route, String> fromNameColumn;
    @FXML
    private TableColumn<Route, String> toXColumn;
    @FXML
    private TableColumn<Route, String> toYColumn;
    @FXML
    private TableColumn<Route, String> toNameColumn;
    @FXML
    private TableColumn<Route, String> distanceColumn;

    @FXML
    private Button createBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button visualizeBtn;
    @FXML
    private Button commandsBtn;

    @FXML
    private Label console;
    private String consolePrefix;

    private final CollectionManager collectionManager = CollectionManager.getInstance();

    private static ScheduledExecutorService scheduler;

    private final RemoveByIdCommand removeByIdCommand = new RemoveByIdCommand();
    private final UpdateCommand updateCommand = new UpdateCommand();

    @FXML
    public void initialize() {
        mainScreen.getStyleClass().add("main-bg");

        logOutBtn.getStyleClass().add("icon-log-out");
        langBtn.getStyleClass().add("icon-lang-btn");

        username.getStyleClass().addAll("heading-based", "text-wp-gradient");
        username.setText(User.getInstance().getUserInfo().username());

        sortKey.getStyleClass().addAll("txt-based", "combo-box-based");
        sortValue.getStyleClass().addAll("txt-based", "combo-box-based");

        collectionView.getColumns().forEach(column -> {
            column.setReorderable(false);
            column.setResizable(false);
            column.setSortable(false);
            column.setEditable(false);
            column.getStyleClass().add("table-border");
        });
        collectionView.getStyleClass().addAll("txt-based", "table-based");
        distanceColumn.setStyle("-fx-border-width: 0px 0px 4px 0px;");

        for (Button btn: new Button[]{createBtn, updateBtn, deleteBtn, visualizeBtn, commandsBtn}) btn.getStyleClass().addAll("txt-based", "btn-based");
        deleteBtn.getStyleClass().add("main-delete-btn");
        visualizeBtn.getStyleClass().add("btn-gradient");
        commandsBtn.getStyleClass().add("btn-gradient");

        console.getStyleClass().addAll("txt-based", "error");

        generateAccordingToLocale();

        collectionView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> unlockDML()));
        collectionView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        collectionView.setItems(collectionList);

        sortKey.setItems(sortKeysList);
        sortValue.setItems(sortValuesList);

        String savedSelectedColumn = selectedColumn;
        Object savedSelectedValue = selectedValue;

        generateKeysList();
        sortKey.valueProperty().addListener(sortKeyTrigger);
        sortValue.valueProperty().addListener(sortValueTrigger);

        if (!savedSelectedColumn.isEmpty()) sortKey.setValue(savedSelectedColumn);
        if (!Objects.equals(savedSelectedValue, "")) sortValue.setValue(savedSelectedValue);

        if (!collectionManager.getCollection().isEmpty()) loadTable();
        if (scheduler == null || scheduler.isShutdown()) autoUpdate();

        lockDML();
    }

    @FXML
    private void goLangScreen() throws IOException {
        scheduler.shutdownNow();
        ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.LANG, mainScreen);
    }

    @FXML
    private void goStartScreen() throws IOException {
        scheduler.shutdownNow();

        selectedColumn = "";
        selectedValue = "";

        collectionManager.reset();

        VisualizeController.reset();

        User.getInstance().setUserInfo(null);

        ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.START, mainScreen);
    }

    @FXML
    private void goCreationFormScreen() throws IOException {
        scheduler.shutdownNow();
        ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.CREATION_FORM, mainScreen);
    }

    @FXML
    private void goUpdateFormScreen() throws IOException {
        try {
            Integer selectedId = collectionView.getSelectionModel().getSelectedItem().id();

            updateCommand.submit(getCommandArgs(selectedId));

            scheduler.shutdownNow();
            ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.UPDATE_FORM, mainScreen, selectedId);
        } catch (IOException e) {
            e.printStackTrace();
            ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.DEATH, mainScreen);
        } catch (Exception e) {
            generateConsoleMessage(e.getMessage());
        }
    }

    @FXML
    private void deleteRow() throws IOException {
        try {
            Integer selectedId = collectionView.getSelectionModel().getSelectedItem().id();

            generateConsoleMessage(removeByIdCommand.submit(getCommandArgs(selectedId)));
            collectionManager.removeById(selectedId);

            filterCollection();
        } catch (IOException e) {
            e.printStackTrace();
            ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.DEATH, mainScreen);
        } catch (Exception e) {
            generateConsoleMessage(e.getMessage());
        }
    }

    @FXML
    private void goVisualizeScreen() throws IOException {
        ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.VISUALIZE, mainScreen);
    }

    @FXML
    private void goCommandsScreen() throws IOException {
        scheduler.shutdownNow();
        ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.COMMANDS, mainScreen);
    }

    private void goDeathScreen() {
        try {
            scheduler.shutdownNow();
            ScreenCastHelper.getInstance().setAndGenerate(ScreenCastHelper.Display.DEATH, mainScreen);
        } catch (IOException ignored) {}
    }

    private void sorting() {
        String selected = sortKey.getValue();
        if (selected == null) return;
        selectedColumn = selected;

        sortCollection();
        generateValuesList();
        setAvailabilityValuesList();
    }

    private void filtering() {
        Object selected = sortValue.getValue();
        if (selected == null) return;
        selectedValue = selected;

        filterCollection();
        updateValuesList();
    }

    private void generateKeysList() {
        sortKeysList.addAll("", "username");
        collectionView.getColumns().forEach(column -> sortKeysList.add(column.getText()));

        selectedColumn = "";

        sortKey.valueProperty().removeListener(sortKeyTrigger);
        sortKey.setValue(selectedColumn);
        sortKey.valueProperty().addListener(sortKeyTrigger);

        setAvailabilityValuesList();
    }

    private void generateValuesList() {
        collectionManager.setMap(selectedColumn);

        selectedValue = "";

        sortValue.valueProperty().removeListener(sortValueTrigger);
        sortValue.setValue(selectedValue);
        sortValue.valueProperty().addListener(sortValueTrigger);

        if (!selectedColumn.isEmpty()) fillValuesList(collectionManager.getProcessedValuesList());
    }

    private void updateValuesList() {
        if (Objects.equals(selectedValue, "")) return;

        TreeSet<Object> processedValuesList = collectionManager.getProcessedValuesList();

        processedValuesList.add(selectedValue);
        fillValuesList(processedValuesList);

        sortValue.valueProperty().removeListener(sortValueTrigger);
        sortValue.setValue(selectedValue);
        sortValue.valueProperty().addListener(sortValueTrigger);
    }

    private void fillValuesList(Set<Object> valuesList) {
        sortValuesList.clear();
        sortValuesList.add("");
        sortValuesList.addAll(valuesList);
    }

    private void setAvailabilityValuesList() {
        sortValue.setDisable(selectedColumn.isEmpty());
    }

    private void sortCollection() {
        collectionManager.setComparator(selectedColumn);
        collectionManager.setFilter(selectedColumn, null);

        loadTable();

        lockDML();
    }

    private void filterCollection() {
        collectionManager.setFilter(selectedColumn, selectedValue);

        loadTable();

        lockDML();
    }

    private void lockDML() {
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }

    private void unlockDML() {
        updateBtn.setDisable(false);
        deleteBtn.setDisable(false);
    }

    private void loadTable() {
        List<Route> processedCollection = collectionManager.getProcessedCollectionList();

        collectionList.setAll(processedCollection);
        Platform.runLater(() -> this.routesInfo.setText(("%d " + this.routesInfoPostfix).formatted(processedCollection.size())));

        collectionView.getSelectionModel().clearSelection();
    }

    private void autoUpdate() {
        CollectionUpdater updater = new CollectionUpdater();
        scheduler = new ScheduledThreadPoolExecutor(1, ThreadHelper::toDaemonThread);
        scheduler.scheduleAtFixedRate(() -> scheduleLoop(updater), 0, 5, TimeUnit.SECONDS);
    }

    private void scheduleLoop(CollectionUpdater updater) {
        try {
            if (User.getInstance().getUserInfo() == null) return;

            LinkedList<Object> DBCollectionExpanded = updater.call();

            HashMap<String, HashSet<Integer>> usersAvailability = (HashMap<String, HashSet<Integer>>) DBCollectionExpanded.getFirst();
            TreeSet<Route> DBCollection = (TreeSet<Route>) DBCollectionExpanded.getLast();

            if (!collectionManager.updateCollectionIfNotEquals(DBCollection)) return;

            collectionManager.setUsersAvailability(usersAvailability);
            loadTable();

            lockDML();
        } catch (Exception e) {
            e.printStackTrace();
            goDeathScreen();
        }
    }

    public void generateConsoleMessage(String message) {
        console.setText(consolePrefix + ": " + message);
    }

    private String[] getCommandArgs(Object data) {
        return new String[]{String.valueOf(data)};
    }

    private void generateAccordingToLocale() {
        Locale locale = LocaleManager.getInstance().getCurrentLocale();

        ResourceBundle officialBundle = ResourceBundle.getBundle("client.locale.Official", locale);
        ResourceBundle buttonBundle = ResourceBundle.getBundle("client.locale.Button", locale);

        routesInfoPostfix = officialBundle.getString("route");

        filterLabel.setText(officialBundle.getString("filter") + ":");
        filterLabel.setLayoutX(filterLabel.getLayoutX() - countTextWidth(filterLabel));

        tableLabel.setText(officialBundle.getString("emptyTable"));

        idColumn.setCellValueFactory(data -> new SimpleStringProperty(NumberFormat.getInstance(locale).format(data.getValue().id())));

        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);

        idColumn.setCellValueFactory(data -> getFormattedNumberCellValue(numberFormat, data.getValue().id()));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().name()));
        coordinateXColumn.setCellValueFactory(data -> getFormattedNumberCellValue(numberFormat, data.getValue().coordinates().x()));
        coordinateYColumn.setCellValueFactory(data -> getFormattedNumberCellValue(numberFormat, data.getValue().coordinates().y()));
        creationDateColumn.setCellValueFactory(data -> getFormattedDateCellValue(dateFormat, data.getValue().creationDate()));
        fromXColumn.setCellValueFactory(data -> getFormattedNumberCellValue(numberFormat, data.getValue().from().x()));
        fromYColumn.setCellValueFactory(data -> getFormattedNumberCellValue(numberFormat, data.getValue().from().y()));
        fromZColumn.setCellValueFactory(data -> getFormattedNumberCellValue(numberFormat, data.getValue().from().z()));
        fromNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().from().name()));
        toXColumn.setCellValueFactory(data -> getFormattedNumberCellValue(numberFormat, data.getValue().to().x()));
        toYColumn.setCellValueFactory(data -> getFormattedNumberCellValue(numberFormat, data.getValue().to().y()));
        toNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().to().name()));
        distanceColumn.setCellValueFactory(data -> getFormattedNumberCellValue(numberFormat, data.getValue().distance()));

        createBtn.setText(buttonBundle.getString("create"));
        updateBtn.setText(buttonBundle.getString("update"));
        deleteBtn.setText(buttonBundle.getString("delete"));
        visualizeBtn.setText(buttonBundle.getString("visualize"));
        commandsBtn.setText(buttonBundle.getString("commands"));

        consolePrefix = officialBundle.getString("info");
    }

    private SimpleStringProperty getFormattedNumberCellValue(NumberFormat format, Object value) {
        return new SimpleStringProperty(format.format(value));
    }

    private SimpleStringProperty getFormattedDateCellValue(DateFormat dateFormat, LocalDate localDate) {
        Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        return new SimpleStringProperty(dateFormat.format(date));
    }

    private double countTextWidth(Label label) {
        Text counter = new Text(label.getText());

        counter.setFont(new Font(32));
        counter.applyCss();

        return counter.getLayoutBounds().getWidth() * 1.7;
    }
}
