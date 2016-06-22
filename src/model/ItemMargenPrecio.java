package model;

import java.sql.Date;

public class ItemMargenPrecio extends ItemMargen {
    private double precio;

    public ItemMargenPrecio() {

    }

    public ItemMargenPrecio(Date fecha, double precio) {
        super(fecha);
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public double margen() {
        return this.precio;
    }

    @Override
    public String toString() {
        return "ItemMargenPrecio{" +
                "precio=" + precio +
                '}';
    }
}
