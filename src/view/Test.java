package view;

import controller.SistemaStock;
import model.*;
import model.strategy.*;

import java.sql.Date;

public class Test {
    private SistemaStock sistema;

    public Test() {
        sistema = SistemaStock.getInstancia();
    }

    public static void main(String[] args) {
        Test test = new Test();

       // test.init1();
    }

    private void init1() {
        Articulo art1 = new Articulo("1", "Levadura");

        art1.addItemStock(Date.valueOf("2016-06-10"), 10, 150);
        art1.addItemStock(Date.valueOf("2016-06-10"), 15, 250);
        art1.addItemStock(Date.valueOf("2016-06-10"), 25, 270);

        System.out.println("Unidades       : " + art1.getStock().getCantidad());
        System.out.println("Valorizado PEPS: " + art1.valorizar(new ValorizacionPEPS()));
        System.out.println("Valorizado UEPS: " + art1.valorizar(new ValorizacionUEPS()));
        System.out.println("Valorizado UCPA: " + art1.valorizar(new ValorizacionUCPA()));
        System.out.println("Valorizado PPP : " + art1.valorizar(new ValorizacionPPP()));

        for (ItemStock item: art1.getStock().getItems()) {
            System.out.println(item);
        }

        art1.restarStock(1);

        System.out.println("Unidades       : " + art1.getStock().getCantidad());
        System.out.println("Valorizado PEPS: " + art1.valorizar(new ValorizacionPEPS()));
        System.out.println("Valorizado UEPS: " + art1.valorizar(new ValorizacionUEPS()));
        System.out.println("Valorizado UCPA: " + art1.valorizar(new ValorizacionUCPA()));
        System.out.println("Valorizado PPP : " + art1.valorizar(new ValorizacionPPP()));

        for (ItemStock item: art1.getStock().getItems()) {
            System.out.println(item);
        }
    }

    private void init2() {

    }
}
