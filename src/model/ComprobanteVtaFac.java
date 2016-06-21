package model;

import java.sql.Date;
import java.util.List;

public class ComprobanteVtaFac extends ComprobanteVta {
    public ComprobanteVtaFac() {

    }

    public ComprobanteVtaFac(Date fecha, String nroComprobante, String cliente) {
        super(fecha, nroComprobante, cliente);
    }

    @Override
    public void updateStock() {
        for (ItemComprobante item: items) {
            List<ItemStock> itemsDescontados = item.getArticulo().restarStock(item.getCantidad());

            for (ItemStock itemDescontado: itemsDescontados) {
                ItemMargen margen = new ItemMargenUnidad(itemDescontado.getFecha(), itemDescontado.getCantidadDisponible(), itemDescontado.getPrecio(), item.getPrecio());
                item.getArticulo().getMargen().addItem(margen);
            }
        }
    }
}
