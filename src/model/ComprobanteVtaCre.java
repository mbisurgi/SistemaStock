package model;

import controller.SistemaStock;
import dao.ArticuloDao;

import java.sql.Date;

public class ComprobanteVtaCre extends ComprobanteVta {
    public ComprobanteVtaCre() {

    }

    public ComprobanteVtaCre(Date fecha, String nroComprobante, String cliente) {
        super(fecha, nroComprobante, cliente);
    }

    @Override
    public void updateStock() {
        for (ItemComprobante item: items) {
            Articulo art = SistemaStock.getInstancia().buscarArticulo(item.getArticulo().getNroArticulo());

            ArticuloDao.getInstancia().insertItemMargenPrecio(art, this.fecha, (item.getCantidad() * item.getPrecio()) * -1);
            ItemMargen itemMargen = new ItemMargenPrecio(this.fecha, (item.getCantidad() * item.getPrecio()) * -1);
            item.getArticulo().addItemMargen(itemMargen);
        }
    }
}
