package Calc;

import Notifications.Notify;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    ComboBox<String> Units;
    @FXML
    TextField KMeasure;
    @FXML
    TextField KPrice;
    @FXML
    TextField DMeasure;
    @FXML
    Label Calculated_Price;
    @FXML
    Button ExitButton;

    Stage window = new Stage();

    ObservableList<String> list = FXCollections.observableArrayList("grams", "Kilograms");

    @FXML
    protected void Calculate() {

        double known_measure = 1;
        double known_price = 1;
        double desired_measure = 1;
        int c = setUnits();

        try {
            known_measure = Double.parseDouble(KMeasure.getText());
            known_price = Double.parseDouble(KPrice.getText());
            desired_measure = Double.parseDouble(DMeasure.getText());
        } catch (Exception e) {
            Notify.error("Invalid entry in either of the fields!");
            e.printStackTrace();
        }
        if (c == 1000) {
            known_measure *= 1000;
            known_price *= 1000;
        }
        double calculated_price = (desired_measure * known_price) / known_measure;
        Calculated_Price.setText(String.valueOf(calculated_price));
    }

    @FXML
    protected int setUnits() {

        String unit = Units.getSelectionModel().getSelectedItem();
        int constant = 1;

        if ("kilograms".equals(unit)) {
            constant = 1000;
        }
        return constant;
    }

    @FXML
    protected void exit() {
        window = (Stage) ExitButton.getScene().getWindow();
        window.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Units.setItems(list);
    }
}
