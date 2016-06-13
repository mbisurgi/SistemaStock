package dao;

import model.Articulo;
import model.ItemStock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDao extends AbstractDao {
    private static ArticuloDao instancia = null;

    private ArticuloDao() {

    }

    public static ArticuloDao getInstancia() {
        if (instancia == null) {
            instancia = new ArticuloDao();
        }

        return instancia;
    }

    @Override
    public void insert(Object obj) {

    }

    @Override
    public void update(Object obj) {

    }

    @Override
    public void delete(Object obj) {

    }

    public List<Articulo> getArticulos() {
        List<Articulo> listado = new ArrayList<>();

        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select * From articulos");

            while (rs.next()) {
                Articulo art = new Articulo();

                art.setNroArticulo(rs.getString("nroArticulo"));
                art.setNombreArticulo(rs.getString("nombreArticulo"));
                art.getStock().setItems(getItems(rs.getString("nroArticulo")));

                listado.add(art);
            }
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }

        return listado;
    }

    private List<ItemStock> getItems(String nroArticulo) {
        List<ItemStock> listado = new ArrayList<>();

        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Select * From itemsstock Where nroArticulo = ? And cantidadDisponible > 0";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nroArticulo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ItemStock item = new ItemStock();

                item.setIdItem(rs.getInt("idItem"));
                item.setFecha(rs.getDate("fecha"));
                item.setCantidad(rs.getInt("cantidad"));
                item.setPrecio(rs.getDouble("precio"));
                item.setCantidadDisponible(rs.getInt("cantidadDisponible"));

                listado.add(item);
            }
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }

        return listado;
    }
}
