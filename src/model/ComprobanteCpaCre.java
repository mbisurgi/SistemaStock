package model;

import java.sql.Date;

public class ComprobanteCpaCre extends ComprobanteCpa {
    public ComprobanteCpaCre(Date fecha, String nroComprobante, String proveedor) {
        super(fecha, nroComprobante, proveedor);
    }

    @Override
    public void updateStock() {
        for (ItemComprobante item: items) {
            ItemMargen itemMargen = new ItemMargenPrecio(this.fecha, (item.getCantidad() * item.getPrecio()));

            item.getArticulo().addItemMargen(itemMargen);
        }
    }
}
