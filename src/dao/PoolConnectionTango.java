package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PoolConnectionTango {
    private List<Connection> connections = new ArrayList<>();

    private static PoolConnectionTango instancia = null;

    private PoolConnectionTango() {
        for (int i = 0; i < 5; i++) {
            connections.add(connect());
        }
    }

    public static PoolConnectionTango getInstancia() {
        if (instancia == null) {
            instancia = new PoolConnectionTango();
        }

        return instancia;
    }

    private Connection connect() {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://181.46.86.2:3306;database=;user=;password=;");

            return con;
        } catch (SQLException exSql) {
            System.out.println("Error: " + exSql.getMessage());
            System.out.println("Stack Trace: " + exSql.getStackTrace());
            return null;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            System.out.println("Stack Trace: " + ex.getStackTrace());
            return null;
        }
    }

    public Connection getConnection() {
        Connection con = null;

        if (connections.size() > 0) {
            con = connections.remove(0);
        }
        else {
            con = connect();
        }

        return con;
    }

    public void releaseConnection(Connection con) {
        connections.add(con);
    }
}
