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

        for (ItemStock item: art1.getStock().getItems()) {
            System.out.println(item);
        }

        Comprobante facVta = new ComprobanteVtaFac(Date.valueOf("2016-06-10"), "A0002-00014032", "Maximiliano Bisurgi");
        facVta.addItem(art1, 125, 200);
        facVta.updateStock();

        for (ItemStock item: art1.getStock().getItems()) {
            System.out.println(item);
        }
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
