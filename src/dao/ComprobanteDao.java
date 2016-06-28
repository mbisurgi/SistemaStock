package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComprobanteDao extends AbstractDao{
    private static ComprobanteDao instancia = null;

    private ComprobanteDao() {

    }

    public static ComprobanteDao getInstancia() {
        if (instancia == null) {
            instancia = new ComprobanteDao();
        }

        return instancia;
    }

    @Override
    public void insert(Object obj) {
        Comprobante comp = (Comprobante)obj;

        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Insert Into comprobantes (tipo, comp, nroComprobante, fecha, razonSocial) Values (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            if (comp.getClass() == ComprobanteCpaFac.class) {
                ComprobanteCpaFac cpaFac = (ComprobanteCpaFac)comp;

                ps.setString(1, "CPA");
                ps.setString(2, "FAC");
                ps.setString(3, cpaFac.getNroComprobante());
                ps.setDate(4, cpaFac.getFecha());
                ps.setString(5, cpaFac.getProveedor());
            }

            if (comp.getClass() == ComprobanteCpaCre.class) {
                ComprobanteCpaCre cpaCre = (ComprobanteCpaCre)comp;

                ps.setString(1, "CPA");
                ps.setString(2, "N/C");
                ps.setString(3, cpaCre.getNroComprobante());
                ps.setDate(4, cpaCre.getFecha());
                ps.setString(5, cpaCre.getProveedor());
            }

            if (comp.getClass() == ComprobanteVtaFac.class) {
                ComprobanteVtaFac vtaFac = (ComprobanteVtaFac)comp;

                ps.setString(1, "VTA");
                ps.setString(2, "FAC");
                ps.setString(3, vtaFac.getNroComprobante());
                ps.setDate(4, vtaFac.getFecha());
                ps.setString(5, vtaFac.getCliente());
            }

            if (comp.getClass() == ComprobanteVtaCre.class) {
                ComprobanteVtaCre vtaCre = (ComprobanteVtaCre)comp;

                ps.setString(1, "VTA");
                ps.setString(2, "NCR");
                ps.setString(3, vtaCre.getNroComprobante());
                ps.setDate(4, vtaCre.getFecha());
                ps.setString(5, vtaCre.getCliente());
            }

            ps.executeUpdate();

            insertItem(comp);
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }
    }

    @Override
    public void update(Object obj) {
        Comprobante comp = (Comprobante)obj;

        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Update comprobantes Set pendiente = ? Where nroComprobante = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "NO");
            ps.setString(2, comp.getNroComprobante());

            ps.executeUpdate();
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }
    }

    @Override
    public void delete(Object obj) {

    }

    public void insertItem(Comprobante comp) {
        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Insert Into itemscomprobante (tipo, comp, nroComprobante, nroArticulo, cantidad, precio) Values (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            if (comp.getClass() == ComprobanteCpaFac.class) {
                ps.setString(1, "CPA");
                ps.setString(2, "FAC");
                ps.setString(3, comp.getNroComprobante());
            }

            if (comp.getClass() == ComprobanteCpaCre.class) {
                ps.setString(1, "CPA");
                ps.setString(2, "N/C");
                ps.setString(3, comp.getNroComprobante());
            }

            if (comp.getClass() == ComprobanteVtaFac.class) {
                ps.setString(1, "VTA");
                ps.setString(2, "FAC");
                ps.setString(3, comp.getNroComprobante());
            }

            if (comp.getClass() == ComprobanteVtaCre.class) {
                ps.setString(1, "VTA");
                ps.setString(2, "NCR");
                ps.setString(3, comp.getNroComprobante());
            }

            for (ItemComprobante item: comp.getItems()) {
                ps.setString(4, item.getArticulo().getNroArticulo());
                ps.setInt(5, item.getCantidad());
                ps.setDouble(6, item.getPrecio());

                ps.executeUpdate();
            }
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }
    }

    public List<Comprobante> getComprobantes() {
        List<Comprobante> listado = new ArrayList<>();

        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select * From comprobantes");

            while (rs.next()) {
                if (rs.getString("tipo").equals("VTA")) {
                    if (rs.getString("comp").equals("FAC")) {
                        ComprobanteVtaFac com = new ComprobanteVtaFac();

                        com.setFecha(rs.getDate("fecha"));
                        com.setNroComprobante(rs.getString("nroComprobante"));
                        com.setCliente(rs.getString("razonSocial"));
                        com.setItems(getItems(rs.getString("tipo"), rs.getString("comp"), rs.getString("nroComprobante")));

                        listado.add(com);
                    }

                    if (rs.getString("comp").equals("NCR")) {
                        ComprobanteVtaCre com = new ComprobanteVtaCre();

                        com.setFecha(rs.getDate("fecha"));
                        com.setNroComprobante(rs.getString("nroComprobante"));
                        com.setCliente(rs.getString("razonSocial"));
                        com.setItems(getItems(rs.getString("tipo"), rs.getString("comp"), rs.getString("nroComprobante")));

                        listado.add(com);
                    }

                    if (rs.getString("comp").equals("DEB")) {

                    }
                }

                if (rs.getString("tipo").equals("CPA")) {
                    if (rs.getString("comp").equals("FAC")) {
                        ComprobanteCpaFac com = new ComprobanteCpaFac();

                        com.setFecha(rs.getDate("fecha"));
                        com.setNroComprobante(rs.getString("nroComprobante"));
                        com.setProveedor(rs.getString("razonSocial"));
                        com.setItems(getItems(rs.getString("tipo"), rs.getString("comp"), rs.getString("nroComprobante")));

                        listado.add(com);
                    }

                    if (rs.getString("comp").equals("N/C")) {
                        ComprobanteCpaCre com = new ComprobanteCpaCre();

                        com.setFecha(rs.getDate("fecha"));
                        com.setNroComprobante(rs.getString("nroComprobante"));
                        com.setProveedor(rs.getString("razonSocial"));
                        com.setItems(getItems(rs.getString("tipo"), rs.getString("comp"), rs.getString("nroComprobante")));

                        listado.add(com);
                    }

                    if (rs.getString("comp").equals("DEB")) {

                    }
                }
            }

        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }

        return listado;
    }

    public List<Comprobante> getComprobantesPendientes() {
        List<Comprobante> listado = new ArrayList<>();

        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Select * From comprobantes Where pendiente = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "SI");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString("tipo").equals("VTA")) {
                    if (rs.getString("comp").equals("FAC")) {
                        ComprobanteVtaFac com = new ComprobanteVtaFac();

                        com.setFecha(rs.getDate("fecha"));
                        com.setNroComprobante(rs.getString("nroComprobante"));
                        com.setCliente(rs.getString("razonSocial"));
                        com.setItems(getItems(rs.getString("tipo"), rs.getString("comp"), rs.getString("nroComprobante")));

                        listado.add(com);
                    }

                    if (rs.getString("comp").equals("NCR")) {
                        ComprobanteVtaCre com = new ComprobanteVtaCre();

                        com.setFecha(rs.getDate("fecha"));
                        com.setNroComprobante(rs.getString("nroComprobante"));
                        com.setCliente(rs.getString("razonSocial"));
                        com.setItems(getItems(rs.getString("tipo"), rs.getString("comp"), rs.getString("nroComprobante")));

                        listado.add(com);
                    }

                    if (rs.getString("comp").equals("DEB")) {

                    }
                }

                if (rs.getString("tipo").equals("CPA")) {
                    if (rs.getString("comp").equals("FAC")) {
                        ComprobanteCpaFac com = new ComprobanteCpaFac();

                        com.setFecha(rs.getDate("fecha"));
                        com.setNroComprobante(rs.getString("nroComprobante"));
                        com.setProveedor(rs.getString("razonSocial"));
                        com.setItems(getItems(rs.getString("tipo"), rs.getString("comp"), rs.getString("nroComprobante")));

                        listado.add(com);
                    }

                    if (rs.getString("comp").equals("N/C")) {
                        ComprobanteCpaCre com = new ComprobanteCpaCre();

                        com.setFecha(rs.getDate("fecha"));
                        com.setNroComprobante(rs.getString("nroComprobante"));
                        com.setProveedor(rs.getString("razonSocial"));
                        com.setItems(getItems(rs.getString("tipo"), rs.getString("comp"), rs.getString("nroComprobante")));

                        listado.add(com);
                    }

                    if (rs.getString("comp").equals("DEB")) {

                    }
                }
            }

        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }

        return listado;
    }

    private List<ItemComprobante> getItems(String tipo, String comp, String nroComprobante) {
        List<ItemComprobante> listado = new ArrayList<>();

        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Select * From itemscomprobante Where tipo = ? And comp = ? And nroComprobante = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tipo);
            ps.setString(2, comp);
            ps.setString(3, nroComprobante);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ItemComprobante item = new ItemComprobante();

                item.setIdItem(rs.getInt("idItem"));
                item.setArticulo(ArticuloDao.getInstancia().getArticulo(rs.getString("nroArticulo")));
                item.setCantidad(rs.getInt("cantidad"));
                item.setPrecio(rs.getDouble("precio"));

                listado.add(item);
            }
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }

        return listado;
    }

    private List<ItemComprobante> getItemsTango(String tipo, String comp, String nroComprobante) {
        List<ItemComprobante> listado = new ArrayList<>();

        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql;

            PreparedStatement ps;

            ResultSet rs;

            if (tipo.equals("VTA")) {
                sql = "Select COD_ARTICU, CANTIDAD, PRECIO_NET From GVA53 Where T_COMP = ? And N_COMP = ?";

                ps = con.prepareStatement(sql);
                ps.setString(1, comp);
                ps.setString(2, nroComprobante);

                rs = ps.executeQuery();

                while (rs.next()) {
                    ItemComprobante item = new ItemComprobante();

                    item.setArticulo(ArticuloDao.getInstancia().getArticulo(rs.getString("COD_ARTICU")));
                    item.setCantidad(rs.getInt("CANTIDAD"));
                    item.setPrecio(rs.getDouble("PRECIO_NET"));

                    listado.add(item);
                }
            }

            if (tipo.equals("CPA")) {
                sql = "Select COD_ARTICU, CANTIDAD, PRECIO_NET From CPA46 Where N_COMP = ?";

                ps = con.prepareStatement(sql);
                ps.setString(1, nroComprobante);

                rs = ps.executeQuery();

                while (rs.next()) {
                    ItemComprobante item = new ItemComprobante();

                    item.setArticulo(ArticuloDao.getInstancia().getArticulo(rs.getString("COD_ARTICU")));
                    item.setCantidad(rs.getInt("CANTIDAD"));
                    item.setPrecio(rs.getDouble("PRECIO_NET"));

                    listado.add(item);
                }
            }
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }

        return listado;
    }

    public void sincronizarComprobantes() {
        HashMap<String, String> comprobantes = new HashMap<>();
        List<Comprobante> comprobantesSincronizar = new ArrayList<>();

        Connection con = PoolConnection.getInstancia().getConnection();
        Connection conTango = PoolConnectionTango.getInstancia().getConnection();

        try {
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select * From comprobantes");

            while (rs.next()) {
                String key = rs.getString("tipo") + rs.getString("comp") + rs.getString("nroComprobante");

                comprobantes.put(key, rs.getString("nroComprobante"));
            }

            String sql;

            sql = "Select T_COMP, N_COMP, FECHA_EMIS, COD_PROVEE From CPA04 Where COD_PROVEE = ?";

            PreparedStatement psTango;

            psTango = conTango.prepareStatement(sql);
            psTango.setString(1, "100001");

            ResultSet rsTango;

            rsTango = psTango.executeQuery();

            while (rsTango.next()) {
                String key = "CPA" + rsTango.getString("T_COMP") + rsTango.getString("N_COMP");

                if (!comprobantes.containsKey(key)) {
                    if (rs.getString("T_COMP").equals("FAC")) {
                        ComprobanteCpaFac comp = new ComprobanteCpaFac();

                        comp.setFecha(rsTango.getDate("FECHA_EMIS"));
                        comp.setNroComprobante(rsTango.getString("N_COMP"));
                        comp.setProveedor(rsTango.getString("COD_PROVEE"));
                        comp.setItems(getItemsTango("CPA", "FAC", rsTango.getString("N_COMP")));

                        comprobantesSincronizar.add(comp);
                    }

                    if (rs.getString("T_COMP").equals("N/C")) {
                        ComprobanteCpaCre comp = new ComprobanteCpaCre();

                        comp.setFecha(rsTango.getDate("FECHA_EMIS"));
                        comp.setNroComprobante(rsTango.getString("N_COMP"));
                        comp.setProveedor(rsTango.getString("COD_PROVEE"));
                        comp.setItems(getItemsTango("VTA", "N/C", rsTango.getString("N_COMP")));

                        comprobantesSincronizar.add(comp);
                    }
                }
            }

            sql = "Select T_COMP, N_COMP, FECHA_EMIS, COD_CLIENT From GVA12";

            psTango = conTango.prepareStatement(sql);

            rsTango = psTango.executeQuery();

            while (rsTango.next()) {
                String key = "VTA" + rsTango.getString("T_COMP") + rsTango.getString("N_COMP");

                if (!comprobantes.containsKey(key)) {
                    if (rs.getString("T_COMP").equals("FAC")) {
                        ComprobanteVtaFac comp = new ComprobanteVtaFac();

                        comp.setFecha(rsTango.getDate("FECHA_EMIS"));
                        comp.setNroComprobante(rsTango.getString("N_COMP"));
                        comp.setCliente(rsTango.getString("COD_CLIENT"));
                        comp.setItems(getItemsTango("VTA", "FAC", rsTango.getString("N_COMP")));

                        comprobantesSincronizar.add(comp);
                    }

                    if (rs.getString("T_COMP").equals("NCR")) {
                        ComprobanteVtaCre comp = new ComprobanteVtaCre();

                        comp.setFecha(rsTango.getDate("FECHA_EMIS"));
                        comp.setNroComprobante(rsTango.getString("N_COMP"));
                        comp.setCliente(rsTango.getString("COD_CLIENT"));
                        comp.setItems(getItemsTango("VTA", "NCR", rsTango.getString("N_COMP")));

                        comprobantesSincronizar.add(comp);
                    }
                }
            }

            for (Comprobante com: comprobantesSincronizar) {
                insert(com);
            }
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }
    }
}
