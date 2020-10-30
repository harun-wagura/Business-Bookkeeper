package Operations;

import FilesManager.Item;
import FilesManager.ResourceManager;
import base.ListViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    @FXML
    Label itemName;
    @FXML
    ListView<String> names;
    @FXML
    ListView<String> quantities;
    @FXML
    ListView<String> costs;
    @FXML
    Label price;

    String key = ListViewManager.selectedItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemName.setText(ListViewManager.selectedItem);

        try {
            names.setItems(dumper1());
            quantities.setItems(dumper2());
            costs.setItems(dumper3());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Returns ingredients
    public ObservableList<String> dumper1() throws IOException, ClassNotFoundException {

        ObservableList<String> list1 = FXCollections.observableArrayList();
        Hashtable<String, Item> hashtable = ResourceManager.read();
        Item item = hashtable.get(key);
        price.setText(item.getPrice());
        ArrayList<String> names = item.getIngredients();
        list1.addAll(names);
        return list1;
    }

    // Returns quantities
    public ObservableList<String> dumper2() throws IOException, ClassNotFoundException {
        ObservableList<String> list2 = FXCollections.observableArrayList();
        Hashtable<String, Item> hashtable = ResourceManager.read();
        Item item = hashtable.get(key);
        ArrayList<String> quantities = item.getQuantities();
        list2.addAll(quantities);
        return list2;
    }

    // Returns costs
    public ObservableList<String> dumper3() throws IOException, ClassNotFoundException {
        ObservableList<String> list3 = FXCollections.observableArrayList();
        Hashtable<String, Item> hashtable = ResourceManager.read();
        Item item = hashtable.get(key);
        ArrayList<String> values = item.getValues();
        list3.addAll(values);
        return list3;
    }
}
