package model;

import java.sql.Date;

public class ComprobanteVtaFac extends ComprobanteVta {
    public ComprobanteVtaFac(Date fecha, String nroComprobante, String cliente) {
        super(fecha, nroComprobante, cliente);
    }

    @Override
    public void updateStock() {

    }
}
