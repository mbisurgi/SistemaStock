package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public abstract class Comprobante {
    protected Date fecha;
    protected String nroComprobante;
    protected List<ItemComprobante> items;

    public Comprobante(Date fecha, String nroComprobante) {
        this.fecha = fecha;
        this.nroComprobante = nroComprobante;
        this.items = new ArrayList<>();
    }

    public void addItem(Articulo articulo, int cantidad, double precio) {
        ItemComprobante item = new ItemComprobante(articulo, cantidad, precio);

        items.add(item);
    }

    public double getTotal() {
        double total = 0;

        for (ItemComprobante item: items) {
            total = total + item.getSubTotal();
        }

        return total;
    }
}
