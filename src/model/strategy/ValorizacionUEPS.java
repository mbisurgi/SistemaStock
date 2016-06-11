package model.strategy;

import model.ItemStock;

import java.util.List;
import java.util.ListIterator;

public class ValorizacionUEPS extends Valorizacion {
    @Override
    public double valorizar(List<ItemStock> items, int cantidad) {
        double valor = 0;

        ListIterator<ItemStock> iterator = items.listIterator(items.size());

        while (iterator.hasPrevious()) {
            valor = valor + iterator.previous().getValorizado();
        }

        return valor;
    }
}
