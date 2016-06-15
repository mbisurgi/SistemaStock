package model;

import java.util.ArrayList;
import java.util.List;

public class Margen {
    private List<ItemMargen> items;

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
}
