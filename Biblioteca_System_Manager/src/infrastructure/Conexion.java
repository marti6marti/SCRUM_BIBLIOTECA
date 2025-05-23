package infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://mysqlscrum-iespoblenou-6495.d.aivencloud.com:26435/defaultdb?"
            + "sslMode=REQUIRED&"
            + "useSSL=true&"
            + "requireSSL=true&"
            + "serverTimezone=UTC&"
            + "allowPublicKeyRetrieval=true";
    private static final String USERNAME = "avnadmin";
    private static final String PASSWORD = "AVNS_Ico65QJgEn-rjTG7_Bq";

    // Bloque estático para registrar el driver
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver JDBC de MySQL", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}