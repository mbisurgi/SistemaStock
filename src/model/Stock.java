package model;

import model.strategy.Valorizacion;
import model.strategy.ValorizacionPEPS;
import model.strategy.ValorizacionUEPS;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Stock {
    private List<ItemStock> items;
    private Valorizacion valorizacion;

    public Stock() {
        this.items = new ArrayList<>();
        this.valorizacion = new ValorizacionUEPS();
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

    public List<ItemStock> restarStock(int cantidad) {
        int cantidadResta = cantidad;
        List<ItemStock> itemsDescontados = new ArrayList<>();
        ItemStock itemDescontado;

            for (ItemStock item: items) {
                int cantidadDisponible = item.getCantidadDisponible();

                if (cantidadDisponible >= cantidadResta) {
                    itemDescontado = new ItemStock();
                    itemDescontado.setFecha(item.getFecha());
                    itemDescontado.setCantidad(item.getCantidad());
                    itemDescontado.setPrecio(item.getPrecio());
                    itemDescontado.setCantidadDisponible(cantidadResta);

                    itemsDescontados.add(itemDescontado);

                    item.setCantidadDisponible(cantidadDisponible - cantidadResta);
                    cantidadResta =  0;
                    break;
                }

                if (cantidadDisponible < cantidadResta) {
                    itemDescontado = new ItemStock();
                    itemDescontado.setFecha(item.getFecha());
                    itemDescontado.setCantidad(item.getCantidad());
                    itemDescontado.setPrecio(item.getPrecio());
                    itemDescontado.setCantidadDisponible(item.getCantidadDisponible());

                    itemsDescontados.add(itemDescontado);

                    item.setCantidadDisponible(0);
                    cantidadResta = cantidadResta - cantidadDisponible;
                }
            }

        return itemsDescontados;
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
