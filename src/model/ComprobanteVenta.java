package model;

import model.bridge.Tipo;

import java.sql.Date;

public class ComprobanteVenta extends Comprobante {
    public ComprobanteVenta(Tipo tipo, Date fecha, String nroComprobante) {
        super(tipo, fecha, nroComprobante);
    }
}
