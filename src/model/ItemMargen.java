package model;

import java.sql.Date;

public abstract class ItemMargen {
    protected int idItem;
    protected Date fecha;

    public ItemMargen() {

    }

    public ItemMargen(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public abstract double margen();

    @Override
    public String toString() {
        return "ItemMargen{" +
                "fecha=" + fecha +
                '}';
    }
}
