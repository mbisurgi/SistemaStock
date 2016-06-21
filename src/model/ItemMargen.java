package model;

import java.sql.Date;

public abstract class ItemMargen {
    protected Date fecha;

    public ItemMargen(Date fecha) {
        this.fecha = fecha;
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
