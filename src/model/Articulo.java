package model;

import model.strategy.Valorizacion;

import java.sql.Date;
import java.util.List;

public class Articulo {
    private String nroArticulo;
    private String nombreArticulo;
    private Stock stock;
    private Margen margen;

    public Articulo() {
        this.stock = new Stock();
        this.margen = new Margen();
    }

    public Articulo(String nroArticulo, String nombreArticulo) {
        this.nroArticulo = nroArticulo;
        this.nombreArticulo = nombreArticulo;
        this.stock = new Stock();
        this.margen = new Margen();
    }

    public String getNroArticulo() {
        return nroArticulo;
    }

    public void setNroArticulo(String nroArticulo) {
        this.nroArticulo = nroArticulo;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Margen getMargen() {
        return margen;
    }

    public void setMargen(Margen margen) {
        this.margen = margen;
    }

    public void addItemStock(Date fecha, int cantidad, double precio) {
        this.stock.addItem(fecha, cantidad, precio);
    }

    public List<ItemStock> restarStock(int cantidad) {
        return this.stock.restarStock(cantidad);
    }

    public double valorizar(Valorizacion valorizacion) {
        stock.setValorizacion(valorizacion);

        return stock.valorizar();
    }

    public void addItemMargen(ItemMargen item) {
        this.margen.addItem(item);
    }

    public double margen() {
        return this.margen.margen();
    }
}
