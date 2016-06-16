package controller;

import dao.ArticuloDao;
import model.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SistemaStock {
    private static SistemaStock instancia = null;

    private List<Articulo> articulos;
    private List<Comprobante> comprobantes;

    private SistemaStock() {
        //articulos = ArticuloDao.getInstancia().getArticulos();
        articulos = new ArrayList<>();
        comprobantes = new ArrayList<>();

        init();
    }

    private void init() {
        Articulo art1 = new Articulo("1001", "LEVADURA DUQUESA");

        articulos.add(art1);

        Comprobante facCpa = new ComprobanteCpaFac(Date.valueOf("2016-06-10"), "A0001-00004232", "Calsa");
        facCpa.addItem(art1, 100, 150);
        facCpa.addItem(art1, 150, 200);
        facCpa.updateStock();

        for (ItemStock itemStock: art1.getStock().getItems()) {
            System.out.println(itemStock);
        }

        Comprobante facVta = new ComprobanteVtaFac(Date.valueOf("2016-06-10"), "A0002-00014032", "Maximiliano Bisurgi");
        facVta.addItem(art1, 130, 200);
        facVta.updateStock();

        Comprobante creVta = new ComprobanteVtaCre(Date.valueOf("2016-06-10"), "A0003-00003421", "Maximiliano Bisurgi");
        creVta.addItem(art1, 10, 50);
        creVta.updateStock();

        for (ItemStock itemStock: art1.getStock().getItems()) {
            System.out.println(itemStock);
        }

        for (ItemMargen itemMargen: art1.getMargen().getItems()) {
            System.out.println(itemMargen);
        }

        System.out.println(art1.margen());
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
