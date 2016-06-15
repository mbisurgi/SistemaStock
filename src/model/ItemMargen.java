package model;

import java.sql.Date;

public class ItemMargen {
    private Date fecha;

    public ItemMargen(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
