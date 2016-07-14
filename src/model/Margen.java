package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Margen {
    protected List<ItemMargen> items;

    public Margen() {
        this.items = new ArrayList<>();
    }

    public List<ItemMargen> getItems() {
        return items;
    }

    public void setItems(List<ItemMargen> items) {
        this.items = items;
    }

    public void addItem(ItemMargen item) {
        items.add(item);
    }

    public double margen() {
        double margen = 0;

        for (ItemMargen item: items) {
            margen = margen + item.margen();
        }

        return margen;
    }

    public double margenFecha(Date desde, Date hasta) {
        double margen = 0;

        for (ItemMargen item: items) {
            if (desde.compareTo(item.getFecha()) <=0 && hasta.compareTo(item.getFecha()) >=0) {
                margen = margen + item.margen();
            }
        }

        return margen;
    }
}
