package model;

import java.sql.Date;

public abstract class ComprobanteCpa extends Comprobante {
    protected String proveedor;

    public ComprobanteCpa(Date fecha, String nroComprobante, String proveedor) {
        super(fecha, nroComprobante);
        this.proveedor = proveedor;
    }
}
