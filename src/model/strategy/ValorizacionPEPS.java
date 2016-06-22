package model.strategy;

import model.ItemStock;

import java.util.List;

public class ValorizacionPEPS extends Valorizacion {
    @Override
    public double valorizar(List<ItemStock> items, int cantidad) {
        double valor = 0;

        for (ItemStock item: items) {
            valor = valor + item.getValorizado();
        }

        return valor;
    }

    @Override
    public String toString() {
        return "PEPS";
    }
}
