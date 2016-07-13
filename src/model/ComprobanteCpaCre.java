package model;

import controller.SistemaStock;

import java.sql.Date;

public class ComprobanteCpaCre extends ComprobanteCpa {
    public ComprobanteCpaCre() {

    }

    public ComprobanteCpaCre(Date fecha, String nroComprobante, String proveedor) {
        super(fecha, nroComprobante, proveedor);
    }

    @Override
    public void updateStock() {
        for (ItemComprobante item: items) {
            Articulo art = SistemaStock.getInstancia().buscarArticulo(item.getArticulo().getNroArticulo());

            ItemMargen itemMargen = new ItemMargenPrecio(this.fecha, (item.getCantidad() * item.getPrecio()));
            art.addItemMargen(itemMargen);
        }
    }
}
