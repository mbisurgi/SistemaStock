package controller;

import dao.ArticuloDao;
import model.Articulo;
import model.Comprobante;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SistemaStock {
    private static SistemaStock instancia = null;

    private List<Articulo> articulos;
    private List<Comprobante> comprobantes;

    private SistemaStock() {
        articulos = ArticuloDao.getInstancia().getArticulos();
        comprobantes = new ArrayList<>();

        init();
    }

    private void init() {

    }

    public static SistemaStock getInstancia() {
        if (instancia == null) {
            instancia = new SistemaStock();
        }

        return instancia;
    }

    public void insertFactura(Date fecha, String nroComprobante) {

    }
}
