package RecordsPackage;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Data implements Serializable {

    public transient final SimpleStringProperty item;
    public transient final SimpleStringProperty market;
    public transient final SimpleDoubleProperty price;
    public transient final SimpleDoubleProperty cost;
    public transient final SimpleDoubleProperty commission;
    public transient final SimpleStringProperty date;
    public transient final SimpleStringProperty month;
    public transient final SimpleDoubleProperty cumulative;
    private final Long serialVersionUid = 1L;

    public Data(String item, String market, Double price, Double cost, Double commission,
                String date, String month, Double cumulative) {
        this.item = new SimpleStringProperty(item);
        this.market = new SimpleStringProperty(market);
        this.price = new SimpleDoubleProperty(price);
        this.cost = new SimpleDoubleProperty(cost);
        this.commission = new SimpleDoubleProperty(commission);
        this.date = new SimpleStringProperty(date);
        this.month = new SimpleStringProperty(month);
        this.cumulative = new SimpleDoubleProperty(cumulative);
    }

    public String getItem() {
        return item.get();
    }

    public void setItem(String item) {
        this.item.set(item);
    }

    public SimpleStringProperty itemProperty() {
        return item;
    }

    public String getMarket() {
        return market.get();
    }

    public void setMarket(String market) {
        this.market.set(market);
    }

    public SimpleStringProperty marketProperty() {
        return market;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public double getCost() {
        return cost.get();
    }

    public void setCost(double cost) {
        this.cost.set(cost);
    }

    public SimpleDoubleProperty costProperty() {
        return cost;
    }

    public double getCommission() {
        return commission.get();
    }

    public void setCommission(double commission) {
        this.commission.set(commission);
    }

    public SimpleDoubleProperty commissionProperty() {
        return commission;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    @Override
    public String toString() {
        return "Data{" +
                "item=" + item +
                ", market=" + market +
                ", price=" + price +
                ", cost=" + cost +
                ", commission=" + commission +
                ", date=" + date +
                ", month=" + month +
                ", cumulative=" + cumulative +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getMonth() {
        return month.get();
    }

    public void setMonth(String month) {
        this.month.set(month);
    }

    public SimpleStringProperty monthProperty() {
        return month;
    }

    public double getCumulative() {
        return cumulative.get();
    }

    public void setCumulative(double cumulative) {
        this.cumulative.set(cumulative);
    }

    public SimpleDoubleProperty cumulativeProperty() {
        return cumulative;
    }


}
