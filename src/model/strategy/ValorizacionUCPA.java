package model.strategy;

import model.ItemStock;

import java.util.List;

public class ValorizacionUCPA extends Valorizacion {
    @Override
    public double valorizar(List<ItemStock> items, int cantidad) {
        ItemStock ultimaCompra = items.get(items.size() - 1);

        return ultimaCompra.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return "UCPA";
    }
}
