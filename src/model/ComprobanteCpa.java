package model;

import java.sql.Date;

public abstract class ComprobanteCpa extends Comprobante {
    protected String proveedor;

    public ComprobanteCpa() {

    }

    public ComprobanteCpa(Date fecha, String nroComprobante, String proveedor) {
        super(fecha, nroComprobante);
        this.proveedor = proveedor;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
}
