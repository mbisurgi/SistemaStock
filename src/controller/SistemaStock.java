package controller;

import model.Articulo;

import java.util.ArrayList;
import java.util.List;

public class SistemaStock {
    private SistemaStock instancia = null;

    private List<Articulo> articulos;

    private SistemaStock() {
        articulos = new ArrayList<>();

        init();
    }

    private void init() {

    }

    public SistemaStock getInstancia() {
        if (instancia == null) {
            instancia = new SistemaStock();
        }

        return instancia;
    }
}
