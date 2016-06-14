package model;

import java.sql.Date;

public class ComprobanteVtaCre extends ComprobanteVta {
    public ComprobanteVtaCre(Date fecha, String nroComprobante, String cliente) {
        super(fecha, nroComprobante, cliente);
    }

    @Override
    public void updateStock() {

    }
}
