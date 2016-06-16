package model;

import java.sql.Date;

public class ComprobanteCpaFac extends ComprobanteCpa {
    public ComprobanteCpaFac(Date fecha, String nroComprobante, String proveedor) {
        super(fecha, nroComprobante, proveedor);
    }

    @Override
    public void updateStock() {
        for (ItemComprobante item: items) {
            item.getArticulo().addItemStock(fecha, item.getCantidad(), item.getPrecio());
        }
    }
}
