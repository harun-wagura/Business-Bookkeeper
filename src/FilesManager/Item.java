package FilesManager;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {

    private String price;
    private String itemName;
    private ArrayList<String> ingredients;
    private ArrayList<String> values;
    private ArrayList<String> quantities;

    public Item(String price, String itemName, ArrayList<String> ingredients, ArrayList<String> values, ArrayList<String> quantities) {
        this.price = price;
        this.itemName = itemName;
        this.ingredients = ingredients;
        this.values = values;
        this.quantities = quantities;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public ArrayList<String> getQuantities() {
        return quantities;
    }

    public void setQuantities(ArrayList<String> quantities) {
        this.quantities = quantities;
    }
}
