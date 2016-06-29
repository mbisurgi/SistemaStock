package controller;

import dao.ArticuloDao;
import dao.ComprobanteDao;
import model.*;
import model.strategy.Valorizacion;
import model.strategy.ValorizacionUEPS;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SistemaStock {
    private static SistemaStock instancia = null;

    private List<Articulo> articulos;
    private List<Comprobante> comprobantes;

    private SistemaStock() {
        articulos = ArticuloDao.getInstancia().getArticulos();
        //comprobantes = ComprobanteDao.getInstancia().getComprobantesPendientes();

        //init();
    }

    private void init() {
        Articulo art1 = new Articulo("1001", "LEVADURA DUQUESA");
        Articulo art2 = new Articulo("1002", "LEVADURA DUQUESA PREMIUM");

        articulos.add(art1);
        articulos.add(art2);

        Comprobante facCpa1 = new ComprobanteCpaFac(Date.valueOf("2016-06-10"), "A0001-00004232", "Calsa");
        facCpa1.addItem(art1, 100, 150);
        facCpa1.addItem(art2, 150, 200);
        facCpa1.updateStock();

        Comprobante facCpa2 = new ComprobanteCpaFac(Date.valueOf("2016-06-10"), "A0001-00004233", "Calsa");
        facCpa2.addItem(art1, 50, 170);
        facCpa2.addItem(art2, 20, 210);
        facCpa2.updateStock();

        comprobantes.add(facCpa1);
        comprobantes.add(facCpa2);

        System.out.println("STOCK INICIAL");

        for (Articulo art: articulos) {
            System.out.println(art.getNombreArticulo());

            for (ItemStock itemStock: art.getStock().getItems()) {
                System.out.println(itemStock);
            }
        }

        Comprobante facVta1 = new ComprobanteVtaFac(Date.valueOf("2016-06-10"), "A0002-00014032", "Maximiliano Bisurgi");
        facVta1.addItem(art1, 130, 200);
        facVta1.updateStock();

        Comprobante creVta1 = new ComprobanteVtaCre(Date.valueOf("2016-06-10"), "A0003-00003421", "Maximiliano Bisurgi");
        creVta1.addItem(art1, 10, 50);
        creVta1.updateStock();

        Comprobante creCpa1 = new ComprobanteCpaCre(Date.valueOf("2016-06-10"), "A0001-00001232", "Calsa");
        creCpa1.addItem(art1, 5, 25);
        creCpa1.updateStock();

        comprobantes.add(facVta1);
        comprobantes.add(creVta1);
        comprobantes.add(creCpa1);

        System.out.println("STOCK VALORIZADO");

        for (Articulo art: articulos) {
            System.out.println(art.getNombreArticulo());

            System.out.println(art.valorizar(new ValorizacionUEPS()));
        }

        System.out.println("STOCK FINAL");

        for (Articulo art: articulos) {
            System.out.println(art.getNombreArticulo());

            for (ItemStock itemStock: art.getStock().getItems()) {
                System.out.println(itemStock);
            }
        }

        System.out.println("DETALLE MARGEN");

        for (Articulo art: articulos) {
            System.out.println(art.getNombreArticulo());

            for (ItemMargen itemMargen: art.getMargen().getItems()) {
                System.out.println(itemMargen);
            }
        }

        System.out.println("TOTAL MARGEN");

        for (Articulo art: articulos) {
            System.out.println(art.getNombreArticulo());

            System.out.println(art.margen());
        }
    }

    public static SistemaStock getInstancia() {
        if (instancia == null) {
            instancia = new SistemaStock();
        }

        return instancia;
    }

    public void sincronizarArticulos() {
        ArticuloDao.getInstancia().sincronizarArticulos();
    }

    public void sincronizarComprobantes() {
        ComprobanteDao.getInstancia().sincronizarComprobantes();
    }

    public void ingresarStock() {
        List<Comprobante> pendientes = ComprobanteDao.getInstancia().getComprobantesPendientes();

        for (Comprobante comp: pendientes) {
            if (comp.getClass() == ComprobanteCpaFac.class) {
                comp.updateStock();

                //ComprobanteDao.getInstancia().update(comp);
            }
        }
    }

    public void egresarStock() {
        List<Comprobante> pendientes = ComprobanteDao.getInstancia().getComprobantesPendientes();

        for (Comprobante comp: pendientes) {
            if (comp.getClass() == ComprobanteVtaFac.class) {
                comp.updateStock();

                //ComprobanteDao.getInstancia().update(comp);
            }
        }
    }

    public void generarMargenPrecio() {
        List<Comprobante> pendientes = ComprobanteDao.getInstancia().getComprobantesPendientes();

        for (Comprobante comp: pendientes) {
            if (comp.getClass() == ComprobanteVtaCre.class || comp.getClass() == ComprobanteCpaCre.class) {
                comp.updateStock();

                ComprobanteDao.getInstancia().update(comp);
            }
        }
    }

    public double valorizar(String nroArticulo, Valorizacion valorizacion) {
        double valorizado = 0;

        Articulo art = buscarArticulo(nroArticulo);

        if (art != null) {
            art.getStock().setValorizacion(valorizacion);
            valorizado = art.getStock().valorizar();
        }

        return valorizado;
    }

    public Articulo buscarArticulo(String nroArticulo) {
        for (Articulo art: articulos) {
            if (art.getNroArticulo().equals(nroArticulo)) {
                return art;
            }
        }

        return null;
    }
}
