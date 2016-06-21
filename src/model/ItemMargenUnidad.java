package model;

import java.sql.Date;

public class ItemMargenUnidad extends ItemMargen {
    private int cantidad;
    private double precioCpa;
    private double precioVta;

    public ItemMargenUnidad(Date fecha, int cantidad, double precioCpa, double precioVta) {
        super(fecha);
        this.cantidad = cantidad;
        this.precioCpa = precioCpa;
        this.precioVta = precioVta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioCpa() {
        return precioCpa;
    }

    public void setPrecioCpa(double precioCpa) {
        this.precioCpa = precioCpa;
    }

    public double getPrecioVta() {
        return precioVta;
    }

    public void setPrecioVta(double precioVta) {
        this.precioVta = precioVta;
    }

    @Override
    public double margen() {
        double totalCpa = cantidad * precioCpa;
        double totalVta = cantidad * precioVta;

        return  totalVta - totalCpa;
    }

    @Override
    public String toString() {
        return "ItemMargenUnidad{" +
                "cantidad=" + cantidad +
                ", precioCpa=" + precioCpa +
                ", precioVta=" + precioVta +
                '}';
    }
}
