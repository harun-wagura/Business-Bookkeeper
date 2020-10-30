package RecordsPackage;

import FilesManager.Item;
import FilesManager.ResourceManager;
import FilesManager.TablesManager;
import Tables.TableEditManager;
import Tables.TableMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Set;

public class Controller implements Initializable {

    @FXML
    ComboBox<String> Items;
    @FXML
    ComboBox<String> Markets;
    @FXML
    ComboBox<String> size;
    @FXML
    DatePicker datePicker;

    @FXML
    TableView<Data> Database;
    @FXML
    TableColumn<Data, String> ITEM;
    @FXML
    TableColumn<Data, String> MARKET;
    @FXML
    TableColumn<Data, Double> PRICE;
    @FXML
    TableColumn<Data, Double> COST;
    @FXML
    TableColumn<Data, Double> COMMISSION;
    @FXML
    TableColumn<Data, String> DATE;
    @FXML
    TableColumn<Data, String> MONTH;
    @FXML
    TableColumn<Data, Double> CUMULATIVE;

    ObservableList<String> sz = FXCollections.observableArrayList("1/4", "1/2", "F");
    ObservableList<String> markets = FXCollections.observableArrayList("Custom",
            "Jumia", "Glovo", "Catering");

    ObservableList<String> items = FXCollections.observableArrayList();
    ObservableList<Data> records = FXCollections.observableArrayList();
    Stage window = new Stage();

    String selectedSize = "F";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Markets.setItems(markets);
        Items.setItems(items);
        size.setItems(sz);

        ITEM.setCellValueFactory(new PropertyValueFactory<Data, String>("item"));
        MARKET.setCellValueFactory(new PropertyValueFactory<Data, String>("market"));
        PRICE.setCellValueFactory(new PropertyValueFactory<Data, Double>("price"));
        COST.setCellValueFactory(new PropertyValueFactory<Data, Double>("cost"));
        COMMISSION.setCellValueFactory(new PropertyValueFactory<Data, Double>("commission"));
        DATE.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
        MONTH.setCellValueFactory(new PropertyValueFactory<Data, String>("month"));
        CUMULATIVE.setCellValueFactory(new PropertyValueFactory<Data, Double>("cumulative"));

        try {
            records = getRecords();
            Database.setItems(records);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");
        editItem.setOnAction((ActionEvent event) -> {
            tableEdit();
        });

        deleteItem.setOnAction((ActionEvent event) -> {
            try {
                tableDelete();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        contextMenu.getItems().addAll(editItem, deleteItem);
        Database.setContextMenu(contextMenu);

    }

    @FXML
    protected void refresh() throws IOException, ClassNotFoundException {
        Database.setItems(getRecords());
    }

    public ObservableList<Data> getRecords() throws IOException, ClassNotFoundException {

        ArrayList<Data> dataArrayList = TablesManager.read();
        ObservableList<Data> list = FXCollections.observableArrayList();
        list.addAll(dataArrayList);
        return list;
    }

    public ObservableList<String> getItems() throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = ResourceManager.read();
        Set<String> keys = hashtable.keySet();
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(keys);
        items.addAll(list);
        return list;
    }

    public Double[] getValues() throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = ResourceManager.read();
        Item itm = hashtable.get(getSelectedItem());
        Double[] values = new Double[2];
        values[0] = Double.parseDouble(itm.getPrice());
        double sum = 0;
        for (String s : itm.getValues()) {
            sum += Double.parseDouble(s);
        }
        values[1] = sum;
        return values;
    }

    @FXML
    public String getSelectedItem() {
        return Items.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void setSelectedMarket() throws IOException, ClassNotFoundException {
        ResourceManager.fileSignature = Markets.getSelectionModel().getSelectedItem();
        reflect();
    }

    public String getSelectedMarket() {
        return Markets.getSelectionModel().getSelectedItem();
    }

    public void reflect() throws IOException, ClassNotFoundException {
        Items.setItems(getItems());
    }

    public Double getCommission() {
        String market = getSelectedMarket();

        double commission = 0;
        switch (market) {
            case "Jumia":
                commission = 0.25;
                return commission;
            case "Glovo":
                commission = 0.31;
                return commission;
            default:
                commission = 0;
                return commission;
        }
    }

    public String getDate() {
        LocalDate localDate = datePicker.getValue();
        return localDate.toString();
    }

    public String getMonth() {
        String date = getDate();
        char char1 = date.charAt(5);
        char char2 = date.charAt(6);
        String monthNumber = new StringBuilder().append(char1).append(char2).toString();
        String month = "";

        switch (monthNumber) {
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "March";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "July";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "December";
                break;
            default:
                break;
        }
        return month;
    }

    public double getCumulative() throws IOException, ClassNotFoundException {
        double cumulative = 0;
        Double[] values = getValues();
        double price = values[0];
        double cost = values[1];
        price = price * checkSize();
        cost = cost * checkSize();
        cumulative = (price) - (cost + (getCommission() * price));
        return cumulative;
    }

    @FXML
    public double checkSize() {

        selectedSize = size.getSelectionModel().getSelectedItem();
        double realSize = 1;
        switch (selectedSize) {
            case "1/4":
                realSize = 0.25;
                break;
            case "1/2":
                realSize = 0.5;
                break;
            default:
                break;
        }
        return realSize;
    }

    @FXML
    public void insert() throws IOException, ClassNotFoundException {

        String item = getSelectedItem();
        String market = getSelectedMarket();
        Double[] values = getValues();
        double price = values[0];
        double cost = values[1];
        double commission = getCommission();
        String date = getDate();
        String month = getMonth();
        double cumulative = getCumulative();
        price = price * checkSize();
        cost = cost * checkSize();
        Data data = new Data(item, market, price, cost, commission, date, month, cumulative);
        records.add(data);
        Database.setItems(records);
        TablesManager.update(data);

    }

    @FXML
    public void discard() {
        datePicker.setPromptText("");

    }

    protected void tableEdit() {

        Data item = Database.getSelectionModel().getSelectedItem();

        TableEditManager.data = item;
        TableEditManager.itm = item.getItem();
        TableEditManager.market = item.getMarket();
        TableEditManager.price = item.getPrice();
        TableEditManager.cost = item.getCost();
        TableEditManager.commission = item.getCommission();
        TableEditManager.date = item.getDate();
        TableEditManager.month = item.getMonth();
        TableEditManager.totals = item.getCumulative();

        TableMain tableMain = new TableMain();
        try {
            tableMain.start(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void tableDelete() throws IOException, ClassNotFoundException {
        Data item = Database.getSelectionModel().getSelectedItem();
        TablesManager.delete(item);
        refresh();

    }


}
