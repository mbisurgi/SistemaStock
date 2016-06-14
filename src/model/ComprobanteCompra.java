package model;

import model.bridge.Tipo;

import java.sql.Date;

public class ComprobanteCompra extends Comprobante {
    public ComprobanteCompra(Tipo tipo, Date fecha, String nroComprobante) {
        super(tipo, fecha, nroComprobante);
    }
}
