package base;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.io.IOException;

public class Chart {

    @FXML
    PieChart pieChart;

    ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

    public void display(ActionEvent event) throws IOException, ClassNotFoundException {

//        Hashtable<Integer, Data> hashtable = TablesManager.read();
//        ArrayList<Data> list = new ArrayList<>();
//        list.add((Data) hashtable.values());
//
//        int itemsCount = 0;

    }
}


