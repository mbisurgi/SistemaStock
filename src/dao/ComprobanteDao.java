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

    public void insertItem(List<ItemComprobante> item) {
        Connection con = PoolConnection.getInstancia().getConnection();

        try {
            String sql = "Inserto Into itemscomprobante () Values ()";


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

            Statement stTango = conTango.createStatement();

            ResultSet rsTango;

            rsTango = stTango.executeQuery("Select * From CPA04");

            while (rsTango.next()) {
                String key = "CPA" + rs.getString("T_COMP") + rs.getString("N_COMP");

                if (!comprobantes.containsKey(key)) {
                    if (rs.getString("T_COMP").equals("FAC")) {
                        ComprobanteCpaFac comp = new ComprobanteCpaFac();

                        comp.setFecha(rs.getDate("FECHA_EMIS"));
                        comp.setNroComprobante(rs.getString("N_COMP"));
                        comp.setProveedor(rs.getString("COD_PROVEE"));

                        comprobantesSincronizar.add(comp);
                    }

                    if (rs.getString("T_COMP").equals("N/C")) {
                        ComprobanteCpaCre comp = new ComprobanteCpaCre();

                        comp.setFecha(rs.getDate("FECHA_EMIS"));
                        comp.setNroComprobante(rs.getString("N_COMP"));
                        comp.setProveedor(rs.getString("COD_PROVEE"));

                        comprobantesSincronizar.add(comp);
                    }
                }
            }

            rsTango = stTango.executeQuery("Select * From GVA12");

            while (rsTango.next()) {
                String key = "VTA" + rs.getString("T_COMP") + rs.getString("N_COMP");

                if (!comprobantes.containsKey(key)) {
                    if (rs.getString("T_COMP").equals("FAC")) {
                        ComprobanteVtaFac comp = new ComprobanteVtaFac();

                        comp.setFecha(rs.getDate("FECHA_EMIS"));
                        comp.setNroComprobante(rs.getString("N_COMP"));
                        comp.setCliente(rs.getString("COD_CLIENT"));

                        comprobantesSincronizar.add(comp);
                    }

                    if (rs.getString("T_COMP").equals("NCR")) {
                        ComprobanteVtaCre comp = new ComprobanteVtaCre();

                        comp.setFecha(rs.getDate("FECHA_EMIS"));
                        comp.setNroComprobante(rs.getString("N_COMP"));
                        comp.setCliente(rs.getString("COD_CLIENT"));

                        comprobantesSincronizar.add(comp);
                    }
                }
            }
        } catch (SQLException ex) {

        } finally {
            PoolConnection.getInstancia().releaseConnection(con);
        }
    }
}
