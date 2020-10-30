package Tables;

import FilesManager.TablesManager;
import RecordsPackage.Controller;
import RecordsPackage.Data;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class TableEditController {

    @FXML
    TextField itemField;
    @FXML
    TextField marketField;
    @FXML
    TextField priceField;
    @FXML
    TextField costField;
    @FXML
    TextField commissionField;
    @FXML
    DatePicker dateField;
    @FXML
    Button cancel;

    Controller controller = new Controller();

    public void show() {
        itemField.setText(TableEditManager.itm);
        marketField.setText(TableEditManager.market);
        priceField.setText(String.valueOf(TableEditManager.price));
        costField.setText(String.valueOf(TableEditManager.cost));
        commissionField.setText(String.valueOf(TableEditManager.commission));
        dateField.setPromptText(TableEditManager.date);
    }

    @FXML
    public void editTable() throws IOException, ClassNotFoundException {

        show();
        String itm = itemField.getText();
        String market = marketField.getText();
        double price = Double.parseDouble(priceField.getText());
        double cost = Double.parseDouble(costField.getText());
        double cmsn = Double.parseDouble(commissionField.getText());
        String date = dateField.getValue().toString();
        double cumulative = price - (cost + (cmsn * price));

        // Grab the item and edit
        Data selectedData = TableEditManager.data;
        Data original_data = selectedData;
        selectedData.setItem(itm);
        selectedData.setMarket(market);
        selectedData.setPrice(price);
        selectedData.setCost(cost);
        selectedData.setCommission(cmsn);
        selectedData.setDate(date);
        selectedData.setCumulative(cumulative);
        Toolkit.getDefaultToolkit().beep();


        TablesManager.replace(original_data, selectedData);
    }

    @FXML
    protected void discard() {
        Stage window = (Stage) cancel.getScene().getWindow();
        window.close();
    }
}
