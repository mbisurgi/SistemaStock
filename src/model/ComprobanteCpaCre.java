package model;

import java.sql.Date;

public class ComprobanteCpaCre extends ComprobanteCpa {
    public ComprobanteCpaCre(Date fecha, String nroComprobante, String proveedor) {
        super(fecha, nroComprobante, proveedor);
    }

    @Override
    public void updateStock() {

    }
}
