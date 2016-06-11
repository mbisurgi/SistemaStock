package model;

import model.strategy.Valorizacion;

import java.util.Date;

public class Articulo {
    private int nroArticulo;
    private String nombreArticulo;
    private Stock stock;

    public Articulo(int nroArticulo, String nombreArticulo) {
        this.nroArticulo = nroArticulo;
        this.nombreArticulo = nombreArticulo;
        this.stock = new Stock();
    }

    public int getNroArticulo() {
        return nroArticulo;
    }

    public void setNroArticulo(int nroArticulo) {
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

    public void addItem(Date fecha, int cantidad, double precio) {
        this.stock.addItem(fecha, cantidad, precio);
    }

    public void restarStock(int cantidad) {
        this.stock.restarStock(cantidad);
    }

    public double valorizar(Valorizacion valorizacion) {
        stock.setValorizacion(valorizacion);

        return stock.valorizar();
    }
}
