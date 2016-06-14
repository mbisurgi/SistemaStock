package model;

import java.sql.Date;

public abstract class ComprobanteVta extends Comprobante {
    protected String cliente;

    public ComprobanteVta(Date fecha, String nroComprobante, String cliente) {
        super(fecha, nroComprobante);
        this.cliente = cliente;
    }
}
