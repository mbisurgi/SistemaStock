package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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

    public void updateItemStock(int idItemStock, int cantidad) {
        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Update itemsstock Set cantidadDisponible = ? Where idItem = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ps.setInt(2, idItemStock);

            ps.executeUpdate();
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }
    }

    public void insertItemStock(Articulo art, Date fecha, int cantidad, double precio) {
        Connection con = PoolConnection.getInstancia().getConnection();

        ItemStock itemStock = new ItemStock(fecha, cantidad, precio);

        try {
            String sql = "Insert Into itemsstock (nroArticulo, fecha, cantidad, precio, cantidadDisponible) Values (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, art.getNroArticulo());
            ps.setDate(2, itemStock.getFecha());
            ps.setInt(3, itemStock.getCantidad());
            ps.setDouble(4, itemStock.getPrecio());
            ps.setInt(5, itemStock.getCantidadDisponible());

            ps.executeUpdate();
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }
    }

    public void insertItemMargenUnidad(Articulo art, Date fecha, int cantidad, double precioCpa, double precioVta) {
        Connection con = PoolConnection.getInstancia().getConnection();

        ItemMargenUnidad itemMargen = new ItemMargenUnidad(fecha, cantidad, precioCpa, precioVta);

        try {
            String sql = "Insert Into itemsmargen (nroArticulo, fecha, cantidad, precioCpa, precioVta) Values (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, art.getNroArticulo());
            ps.setDate(2, itemMargen.getFecha());
            ps.setInt(3, itemMargen.getCantidad());
            ps.setDouble(4, itemMargen.getPrecioCpa());
            ps.setDouble(5, itemMargen.getPrecioVta());

            ps.executeUpdate();
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }
    }

    public void insertItemMargenPrecio(Articulo art, Date fecha, double precio) {
        Connection con = PoolConnection.getInstancia().getConnection();

        ItemMargenPrecio itemMargen = new ItemMargenPrecio(fecha, precio);

        try {
            String sql = "Insert Into itemsmargen (nroArticulo, fecha, precio) Values (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, art.getNroArticulo());
            ps.setDate(2, itemMargen.getFecha());
            ps.setDouble(3, itemMargen.getPrecio());

            ps.executeUpdate();
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }
    }

    public Articulo getArticulo(String nroArticulo) {
        Articulo art = new Articulo();

        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Select * From articulos Where nroArticulo = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nroArticulo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                art.setNroArticulo(rs.getString("nroArticulo"));
                art.setNombreArticulo(rs.getString("nombreArticulo"));
            }
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }

        return art;
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
                art.getStock().setItems(getItemsStock(rs.getString("nroArticulo")));
                art.getMargen().setItems(getItemsMargen(rs.getString("nroArticulo")));

                listado.add(art);
            }
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }

        return listado;
    }

    private List<ItemStock> getItemsStock(String nroArticulo) {
        List<ItemStock> listado = new ArrayList<>();

        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Select * From itemsstock Where nroArticulo = ? And cantidadDisponible > 0 Order By fecha Asc";

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

    private List<ItemMargen> getItemsMargen(String nroArticulo) {
        List<ItemMargen> listado = new ArrayList<>();

        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Select * From itemsmargen Where nroArticulo = ? Order By fecha Asc";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nroArticulo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getInt("cantidad") != 0) {
                    ItemMargenUnidad item = new ItemMargenUnidad();

                    item.setIdItem(rs.getInt("idItem"));
                    item.setFecha(rs.getDate("fecha"));
                    item.setCantidad(rs.getInt("cantidad"));
                    item.setPrecioCpa(rs.getDouble("precioCpa"));
                    item.setPrecioVta(rs.getDouble("precioVta"));

                    listado.add(item);
                }

                if (rs.getInt("cantidad") == 0) {
                    ItemMargenPrecio item = new ItemMargenPrecio();

                    item.setIdItem(rs.getInt("idItem"));
                    item.setFecha(rs.getDate("fecha"));
                    item.setPrecio(rs.getDouble("precio"));

                    listado.add(item);
                }
            }
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }

        return listado;
    }

    public void sincronizarArticulos() {
        HashMap<String, String> articulos = new HashMap<>();
        List<Articulo> articulosSincronizar = new ArrayList<>();

        Connection con = PoolConnection.getInstancia().getConnection();
        Connection conTango = PoolConnectionTango.getInstancia().getConnection();

        try {
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select * From articulos");

            while (rs.next()) {
                articulos.put(rs.getString("nroArticulo"), rs.getString("nombreArticulo"));
            }

            Statement stTango = conTango.createStatement();

            ResultSet rsTango = stTango.executeQuery("Select COD_ARTICU, DESCRIPCIO From STA11");

            while (rsTango.next()) {
                if (!articulos.containsKey(rsTango.getString("COD_ARTICU"))) {
                    Articulo art = new Articulo();

                    art.setNroArticulo(rsTango.getString("COD_ARTICU"));
                    art.setNombreArticulo(rsTango.getString("DESCRIPCIO"));

                    articulosSincronizar.add(art);
                }
            }

            String sql = "Insert Into articulos (nroArticulo, nombreArticulo) Values (?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            for (Articulo art: articulosSincronizar) {
                ps.setString(1, art.getNroArticulo());
                ps.setString(2, art.getNombreArticulo());

                ps.executeUpdate();
            }
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
            PoolConnectionTango.getInstancia().releaseConnection(conTango);
        }
    }
}
