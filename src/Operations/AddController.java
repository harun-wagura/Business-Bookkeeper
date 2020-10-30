package Operations;

import FilesManager.Item;
import FilesManager.ResourceManager;
import Notifications.Notify;
import base.Progress;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class AddController {
    @FXML
    public TextField itemName;
    @FXML
    TextField price;
    // CLASS DECLARATIONS
    ArrayList<String> namesList = new ArrayList<>();
    ArrayList<String> quantityList = new ArrayList<>();
    ArrayList<String> costList = new ArrayList<>();
    @FXML
    private TextArea names;
    @FXML
    private TextArea quantities;
    @FXML
    private TextArea costs;

    @FXML
    public void cancel() {
        // CLEAR THE TEXT FIELD AND TEXT AREAS
        price.clear();
        itemName.clear();
        names.clear();
        quantities.clear();
        costs.clear();
    }

    @FXML
    public void addItems(ActionEvent event) throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = ResourceManager.read();
        String priceValue = price.getText();
        String item = itemName.getText();
        String namesText = names.getText();
        String quantitiesText = quantities.getText();
        String costsText = costs.getText();

        //Split them by the new line character and add them as lists
        namesList.addAll(Arrays.asList(namesText.split("\\r?\\n")));
        quantityList.addAll(Arrays.asList(quantitiesText.split("\\r?\\n")));
        costList.addAll(Arrays.asList(costsText.split("\\r?\\n")));

        boolean emptyLine = false;
        String found1 = "";
        String found2 = "";
        String found3 = "";
        for (String s : namesList) {
            if (s.equals("")) {
                emptyLine = true;
                found1 = "Name ";
                break;
            }
        }
        for (String s : quantityList) {
            if (s.equals("")) {
                emptyLine = true;
                found2 = "Quantity ";
                break;
            }
        }
        for (String s : costList) {
            if (s.equals("")) {
                emptyLine = true;
                found3 = "Cost ";
                break;
            }
        }
        int count = namesList.size();

        if (item.isEmpty() || namesText.isEmpty() || quantitiesText.isEmpty() || costsText.isEmpty()) {
            Notify.error("There is one or more empty field(s)!");
        } else if (hashtable.containsKey(item)) {
            Notify.error("Item: " + item + " already exists!" + "\n" + "Modify the name");
        } else if (emptyLine) {
            Notify.error("There is a blank entry in field(s) -> " + found1 + found2 + found3);
        } else if (count != quantityList.size() || count != costList.size()) {
            Notify.error("Unmatched field entries!" + "\n" + "Check Ingredients, Quantities and Cost fields");
            //Reset the ArrayLists to prevent re-addition of objects
            namesList.clear();
            quantityList.clear();
            costList.clear();

        } else {
            //Create Object
            Item object = new Item(priceValue, item, namesList, costList, quantityList);

            // UPDATE THE DATA
            ResourceManager.update(object);
            Progress.saved = false;

            // CLEAR THE TEXT FIELD AND TEXT AREAS
            price.clear();
            itemName.clear();
            names.clear();
            quantities.clear();
            costs.clear();
        }
    }
}
