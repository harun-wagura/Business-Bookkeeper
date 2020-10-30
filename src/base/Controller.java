package base;

import Calc.CalcMain;
import FilesManager.Item;
import FilesManager.ResourceManager;
import Notifications.Notify;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Set;

public class Controller implements Initializable {

    @FXML
    MenuItem close;
    @FXML
    Label price;
    @FXML
    Label commission;
    @FXML
    Label returns;
    @FXML
    RadioButton Jumia;
    @FXML
    RadioButton Glovo;
    @FXML
    RadioButton Custom;
    @FXML
    RadioButton Catering;
    @FXML
    ListView<String> markets;
    @FXML
    ComboBox<String> background;
    @FXML
    ComboBox<String> calcs;
    RecordsPackage.Main main = new RecordsPackage.Main();
    // VARIABLE DECLARATIONS
    Stage window = new Stage();
    ObservableList<String> calcList = FXCollections.observableArrayList("Basic", "Modified");
    ObservableList<String> stylesList = FXCollections.observableArrayList("BACKGROUND_1", "BACKGROUND_2", "BACKGROUND_3");
    ObservableList<String> marketsList = FXCollections.observableArrayList("Custom",
            "Jumia", "Glovo", "Catering");
    //FXML DECLARATIONS
    @FXML
    private ListView<String> items;
    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            reflect();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        background.setItems(stylesList);
        markets.setItems(marketsList);
        //calcs.setItems(calcList);
        items.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Ensure only one item is selected

        items.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ObservableList<String> names = items.getSelectionModel().getSelectedItems();
                for (String itm : names) {
                    ListViewManager.selectedItem = itm;
                }
                if (ListViewManager.selectedItem == null) {
                    Notify.error("You have not selected an item to view!");

                } else {
                    try {
                        price.setText(getPrice());
                        returns.setText(String.valueOf(getReturns()));

                        String filename = "/Scenes/Viewer";
                        SceneLoader loader = new SceneLoader();
                        Pane view = loader.getView(filename);
                        borderPane.setCenter(view);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        items.setCellFactory(lv -> {

            ListCell<String> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem editItem = new MenuItem();
            editItem.textProperty().bind(Bindings.format("Edit \"%s\"", cell.itemProperty()));
            editItem.setOnAction(event -> {
                try {
                    setCenterEdit();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

            MenuItem deleteItem = new MenuItem();
            deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()));
            deleteItem.setOnAction(event -> {
                try {
                    Delete();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

            contextMenu.getItems().addAll(editItem, deleteItem);
            cell.textProperty().bind(cell.itemProperty());

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {

                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });

        markets.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    main.start(window);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void reflect() throws IOException, ClassNotFoundException {
        items.setItems(getItems());
    }

    // Grabs the items from stored hash tables
    public String getPrice() throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = ResourceManager.read();
        Item itm = hashtable.get(ListViewManager.selectedItem);
        return itm.getPrice();
    }

    public double getSum() throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = ResourceManager.read();
        Item itm = hashtable.get(ListViewManager.selectedItem);
        ArrayList<String> valuesString = itm.getValues();
        ArrayList<Double> values = new ArrayList<>();
        for (String s : valuesString) {
            double k = 0;
            try {
                k = Double.parseDouble(s);
                values.add(k);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        double sum = 0;
        for (double j : values) {
            sum += j;
        }
        return sum;
    }

    public double getReturns() throws IOException, ClassNotFoundException {
        String priceValue = getPrice();
        double doubleValues = Double.parseDouble(priceValue);
        double returnsValue = getSum();
        return doubleValues - returnsValue;

    }

    public ObservableList<String> getItems() throws IOException, ClassNotFoundException {

        // Grab previous items if they exist
        Hashtable<String, Item> itemsHashTable = ResourceManager.read();
        Set<String> keys = itemsHashTable.keySet();
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(keys);
        return list;
    }

    @FXML
    public void getSelectedRadio() throws IOException, ClassNotFoundException {

        if (Custom.isSelected()) {
            commission.setText("0%");
            ResourceManager.fileSignature = "Custom";
            reflect();
        } else if (Jumia.isSelected()) {
            commission.setText("25%");
            ResourceManager.fileSignature = "Jumia";
            reflect();
        }
        if (Glovo.isSelected()) {
            commission.setText("31%");
            ResourceManager.fileSignature = "Glovo";
            reflect();
        }
        if (Catering.isSelected()) {
            commission.setText("0%");
            ResourceManager.fileSignature = "Catering";
            reflect();
        }
    }

    // MENU FUNCTIONS
    @FXML
    public void setNewStage(ActionEvent event) throws Exception {
        Progress.openStages++;
        main.start(window);
    }

    @FXML
    public void exitStage(ActionEvent event) {

        if (!Progress.saved) {
            Notify.warn("Unsaved changes! Save before exiting");
        } else {
            Progress.openStages--;
            window.close();
            if (Progress.openStages == 0) Platform.exit();
        }
    }

    @FXML
    public void save(ActionEvent event) throws IOException, ClassNotFoundException {

        if (!Progress.saved) { // Means data has been changed
            ResourceManager.write();
            Notify.inform("Changes saved!");
            items.setItems(getItems());

        } else {
            Notify.inform("No changes made!");
        }
        Progress.saved = true; // Set the flag to true

    }

    // BUTTON FUNCTIONS
    @FXML
    public void setCenterAdd(ActionEvent event) throws IOException {
        String filename = "/Scenes/Add";
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView(filename);
        borderPane.setCenter(view);
    }

    @FXML
    public void setCenterView(ActionEvent event) throws IOException, ClassNotFoundException {
        ObservableList<String> names = items.getSelectionModel().getSelectedItems();
        for (String itm : names) {
            ListViewManager.selectedItem = itm;
        }
        if (ListViewManager.selectedItem == null) {
            Notify.error("You have not selected an item to view!");

        } else {
            price.setText(getPrice());
            returns.setText(String.valueOf(getReturns()));

            String filename = "/Scenes/Viewer";
            SceneLoader loader = new SceneLoader();
            Pane view = loader.getView(filename);
            borderPane.setCenter(view);
        }
    }

    @FXML
    public void setCenterEdit() throws IOException, ClassNotFoundException {

        ObservableList<String> names = items.getSelectionModel().getSelectedItems();
        for (String itm : names) {
            ListViewManager.selectedItem = itm;
        }
        if (ListViewManager.selectedItem == null) {
            Notify.error("You have not selected an item to edit!");
        } else {
            price.setText(getPrice());
            returns.setText(String.valueOf(getReturns()));
            String filename = "/Scenes/Edit";
            SceneLoader sceneLoader = new SceneLoader();
            Pane view = sceneLoader.getView(filename);
            borderPane.setCenter(view);
        }
    }

    @FXML
    public void Delete() throws IOException, ClassNotFoundException {

        ObservableList<String> names = items.getSelectionModel().getSelectedItems();
        for (String itm : names) {
            ListViewManager.selectedItem = itm;
        }
        if (ListViewManager.selectedItem == null) {
            Notify.error("You have not selected an item to delete!");
        } else {
            ResourceManager.delete(ListViewManager.selectedItem);
            Progress.saved = false;
            items.setItems(getItems());
        }
    }


    @FXML
    protected void setHomeScene() {

    }

    @FXML
    protected void setChart() {

    }

    protected String setBackground() throws IOException {
        return background.getSelectionModel().getSelectedItem();
    }

    public Scene getBackgroundScene() {
        return background.getScene();
    }

    public Stage getBackgroundStage() {
        return (Stage) getBackgroundScene().getWindow();
    }

    public String setBackgroundImage(String selectedBackground) {

        switch (selectedBackground) {
            case "BACKGROUND_1":
                selectedBackground = "Base.css";
                break;
            case "BACKGROUND_2":
                selectedBackground = "Base2.css";
                break;
            case "BACKGROUND_3":
                selectedBackground = "Base3.css";
                break;
            default:
                break;
        }
        return selectedBackground;


    }

    @FXML
    public void setImage() throws IOException {
        String selectedBackground = setBackgroundImage(setBackground());
        Scene scene = getBackgroundScene();
        scene.getStylesheets().add(getClass().getResource("/Styles/" + selectedBackground).toExternalForm());
        Stage stage = getBackgroundStage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void chooseCalculator() throws Exception {
        String calc = calcs.getSelectionModel().getSelectedItem();
        if (calc.equals("Modified")) {
            CalcMain calcMain = new CalcMain();
            calcMain.start(window);
        } else {
            callCalc();
        }
    }

    public void callCalc() throws IOException {
        String cmd = "calc.exe"; // Call windows 10 Calculator
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        processBuilder.start();
    }
}



