package model;

import java.sql.Date;

public class ComprobanteFactura extends Comprobante {
    public ComprobanteFactura(Date fecha, String nroComprobante) {
        super(fecha, nroComprobante);
    }
}
