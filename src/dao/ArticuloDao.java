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
        Articulo art = (Articulo)obj;


    }

    @Override
    public void delete(Object obj) {

    }

    public  void updateItems(Articulo art) {
        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            for (ItemStock itemStock: art.getStock().getItems()) {
                if (itemStock.getIdItem() != 0) {
                    updateItemStock(itemStock);
                } else {
                    insertItemStock(art.getNroArticulo(), itemStock);
                }
            }

            for (ItemMargen itemMargen: art.getMargen().getItems()) {
                if (itemMargen.getClass() == ItemMargenUnidad.class && itemMargen.getIdItem() == 0) {
                    ItemMargenUnidad itemMargenUnidad = (ItemMargenUnidad)itemMargen;
                    insertItemMargenUnidad(art.getNroArticulo(), itemMargenUnidad);
                }

                if (itemMargen.getClass() == ItemMargenPrecio.class && itemMargen.getIdItem() == 0) {
                    ItemMargenPrecio itemMargenPrecio = (ItemMargenPrecio)itemMargen;
                    insertItemMargenPrecio(art.getNroArticulo(), itemMargenPrecio);
                }
            }
        } catch (Exception ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }
    }

    public void updateItemStock(ItemStock item) {
        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Update itemsstock Set cantidadDisponible = ? Where idItem = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, item.getCantidadDisponible());
            ps.setInt(2, item.getIdItem());

            ps.executeUpdate();
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }
    }

    public void insertItemStock(String nroArticulo, ItemStock item) {
        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Insert Into itemsstock (nroArticulo, fecha, cantidad, precio, cantidadDisponible) Values (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nroArticulo);
            ps.setDate(2, item.getFecha());
            ps.setInt(3, item.getCantidad());
            ps.setDouble(4, item.getPrecio());
            ps.setInt(5, item.getCantidadDisponible());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                item.setIdItem(rs.getInt(1));
            }
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }
    }

    public void insertItemMargenUnidad(String nroArticulo, ItemMargenUnidad item) {
        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Insert Into itemsmargen (nroArticulo, fecha, cantidad, precioCpa, precioVta) Values (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nroArticulo);
            ps.setDate(2, item.getFecha());
            ps.setInt(3, item.getCantidad());
            ps.setDouble(4, item.getPrecioCpa());
            ps.setDouble(5, item.getPrecioVta());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                item.setIdItem(rs.getInt(1));
            }
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }
    }

    public void insertItemMargenPrecio(String nroArticulo, ItemMargenPrecio item) {
        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Insert Into itemsmargen (nroArticulo, fecha, precio) Values (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nroArticulo);
            ps.setDate(2, item.getFecha());
            ps.setDouble(3, item.getPrecio());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                item.setIdItem(rs.getInt(1));
            }
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
