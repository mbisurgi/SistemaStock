package model;

import com.sun.org.apache.bcel.internal.generic.IXOR;
import model.strategy.Valorizacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Stock {
    private List<ItemStock> items;
    private Valorizacion valorizacion;

    public Stock() {
        this.items = new ArrayList<>();
    }

    public List<ItemStock> getItems() {
        return items;
    }

    public void setItems(List<ItemStock> items) {
        this.items = items;
    }

    public Valorizacion getValorizacion() {
        return valorizacion;
    }

    public void setValorizacion(Valorizacion valorizacion) {
        this.valorizacion = valorizacion;
    }

    public void addItem(Date fecha, int cantidad, double precio) {
        ItemStock item = new ItemStock(fecha, cantidad, precio);

        items.add(item);
    }

    public void restarStock(int cantidad) {
        int cantidadResta = cantidad;

            for (ItemStock item: items) {
                int cantidadDisponible = item.getCantidadDisponible();

                if (cantidadDisponible >= cantidadResta) {
                    item.setCantidadDisponible(cantidadDisponible - cantidadResta);
                    cantidadResta =  0;
                    break;
                }

                if (cantidadDisponible < cantidadResta) {
                    item.setCantidadDisponible(0);
                    cantidadResta = cantidadResta - cantidadDisponible;
                }
            }
    }

    public int getCantidad() {
        int cantidad = 0;

        for (ItemStock item: items) {
            cantidad = cantidad + item.getCantidadDisponible();
        }

        return cantidad;
    }

    public double valorizar() {
        return this.valorizacion.valorizar(items, getCantidad());
    }
}
