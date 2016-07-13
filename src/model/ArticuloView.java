package model;

public class ArticuloView {
    private String nroArticulo;
    private String nombreArticulo;
    private double margen;

    public ArticuloView(String nroArticulo, String nombreArticulo, double margen) {
        this.nroArticulo = nroArticulo;
        this.nombreArticulo = nombreArticulo;
        this.margen = margen;
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
}
