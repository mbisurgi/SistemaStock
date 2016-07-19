package model;

public class ArticuloView {
    private String nroArticulo;
    private String nombreArticulo;
    private double margen;
    private int stockUni;
    private double stockVal;

    public ArticuloView(String nroArticulo, String nombreArticulo, double margen, int stockUni, double stockVal) {
        this.nroArticulo = nroArticulo;
        this.nombreArticulo = nombreArticulo;
        this.margen = margen;
        this.stockUni = stockUni;
        this.stockVal = stockVal;
    }

    public String getNroArticulo() {
        return nroArticulo;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public double getMargen() {
        return margen;
    }

    public int getStockUni() {
        return stockUni;
    }

    public double getStockVal() {
        return stockVal;
    }
}
