package Operations;

import FilesManager.Item;
import FilesManager.ResourceManager;
import Notifications.Notify;
import base.ListViewManager;
import base.Progress;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.ResourceBundle;

public class EditController implements Initializable {

    @FXML
    public TextField itemName;
    @FXML
    TextField price;
    String key = ListViewManager.selectedItem;
    URL url;
    ResourceBundle resourceBundle;
    // Templates
    ArrayList<String> original_ingredients = new ArrayList<>();
    ArrayList<String> original_quantities = new ArrayList<>();
    ArrayList<String> original_values = new ArrayList<>();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ArrayList<StringBuilder> array = display();

        try {
            price.setText(getPrice());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        itemName.setText(ListViewManager.selectedItem);
        names.setText(array.get(0).toString());
        quantities.setText(array.get(1).toString());
        costs.setText(array.get(2).toString());
    }

    public ArrayList<StringBuilder> display() {
        try {
            original_ingredients.addAll(getKeyIngredients());
            original_quantities.addAll(getKeyQuantities());
            original_values.addAll(getKeyValues());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //DUMP THE VALUES TO TEXT AREAS
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();

        for (String s : original_ingredients) {
            sb1.append(s).append("\n");
        }
        for (String s : original_quantities) {
            sb2.append(s).append("\n");
        }
        for (String s : original_values) {
            sb3.append(s).append("\n");
        }
        ArrayList<StringBuilder> sb = new ArrayList<>();
        sb.add(sb1);
        sb.add(sb2);
        sb.add(sb3);

        return sb;
    }

    public String getPrice() throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = ResourceManager.read();
        Item itm = hashtable.get(ListViewManager.selectedItem);
        return itm.getPrice();
    }

    public ArrayList<String> getKeyIngredients() throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = ResourceManager.read();
        Item itm = hashtable.get(ListViewManager.selectedItem);
        ArrayList<String> list = (itm.getIngredients());
        return list;
    }

    public ArrayList<String> getKeyQuantities() throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = ResourceManager.read();
        Item itm = hashtable.get(key);
        ArrayList<String> list = (itm.getQuantities());
        return list;
    }

    public ArrayList<String> getKeyValues() throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = ResourceManager.read();
        Item itm = hashtable.get(key);
        ArrayList<String> list = (itm.getValues());
        return list;
    }

    @FXML
    public void cancel() {
        // CLEAR THE TEXT FIELD AND TEXT AREAS
        price.clear();
        itemName.clear();
        names.clear();
        quantities.clear();
        costs.clear();

//        //INITIALIZE ONCE AGAIN
//        ArrayList<StringBuilder> array = display();
//
//        itemName.setText(ListViewManager.selectedItem);
//        names.setText(array.get(0).toString());
//        quantities.setText(array.get(1).toString());
//        costs.setText(array.get(2).toString());
    }

    @FXML
    public void edit(ActionEvent event) throws IOException, ClassNotFoundException {

        Hashtable<String, Item> table = ResourceManager.read();
        Item itm1 = table.get(key);

        //GRAB THE CURRENT VALUES
        String priceValue = price.getText();
        String item = itemName.getText();
        String namesText = names.getText();
        String quantitiesText = quantities.getText();
        String costsText = costs.getText();

        //Split them by the new line character and add them as lists
        namesList.addAll(Arrays.asList(namesText.split("\\r?\\n")));
        quantityList.addAll(Arrays.asList(quantitiesText.split("\\r?\\n")));
        costList.addAll(Arrays.asList(costsText.split("\\r?\\n")));

        // ENSURE ALL NECESSARY CHECKS ARE DONE
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
        } else if (emptyLine) {
            Notify.error("There is a blank entry in field(s) -> " + found1 + found2 + found3);
        } else if (count != quantityList.size() || count != costList.size()) {
            Notify.error("Unmatched field entries!" + "\n" + "Check Names, Quantities and Cost fields");
            //Reset the ArrayLists to prevent re-addition of objects
            namesList.clear();
            quantityList.clear();
            costList.clear();

        } else {

            //LISTEN FOR CHANGES... Check sizes first
//            if (original_ingredients.size() != namesList.size() ||
//                    original_quantities.size() != quantityList.size() ||
//                    original_values.size() != costList.size()
//            ) { Progress.saved = false; }
//
//            if (!CollectionUtils.isEqualCollection(original_ingredients, namesList) ||
//                    !CollectionUtils.isEqualCollection(original_quantities, quantityList) ||
//                    !CollectionUtils.isEqualCollection(original_values, costList))
//            { Progress.saved = false; }
// WE DON'T NEED ALL THIS. JUST REPLACE THE ITEM OBJECT
            //Create Object
            Item itm2 = new Item(priceValue, item, namesList, quantityList, costList);
            for (String s : itm2.getIngredients()) {
                System.out.println(s);
            }
            ResourceManager.replace(itm1, itm2);
            Progress.saved = false;

            // CLEAR THE TEXT FIELD AND TEXT AREAS
            itemName.clear();
            price.clear();
            names.clear();
            quantities.clear();
            costs.clear();
        }
    }
}
