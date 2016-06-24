package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;
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

    }

    @Override
    public void update(Object obj) {

    }

    @Override
    public void delete(Object obj) {

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

                    if (rs.getString("comp").equals("CRE")) {
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

                    if (rs.getString("comp").equals("CRE")) {
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

    }
}
