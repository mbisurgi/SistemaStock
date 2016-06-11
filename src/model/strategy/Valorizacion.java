package model.strategy;

import model.ItemStock;

import java.util.List;

public abstract class Valorizacion {
    public abstract double valorizar(List<ItemStock> items, int cantidad);
}
