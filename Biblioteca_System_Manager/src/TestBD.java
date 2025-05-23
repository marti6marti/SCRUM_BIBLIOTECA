import infrastructure.Conexion;
import java.sql.Connection;
import java.sql.SQLException;

public class TestBD {
    public static void main(String[] args) {
        try {
            System.out.println("=== INICIO DE PRUEBAS ===");

            // 1. Probar conexión básica
            testConexionBasica();

        } catch (Exception e) {
            System.err.println("\n Error durante las pruebas:");
            e.printStackTrace();
            mostrarSugerenciasError(e);
        }
    }

    private static void testConexionBasica() throws SQLException {
        System.out.println("\n--- Probando conexión básica ---");
        Conexion conexion = new Conexion();
        try (Connection conn = conexion.getConnection()) {
            System.out.println("  - Conexión exitosa a la base de datos");
            System.out.println("  - Producto: " + conn.getMetaData().getDatabaseProductName());
            System.out.println("  - Versión: " + conn.getMetaData().getDatabaseProductVersion());
            System.out.println("  - URL: " + conn.getMetaData().getURL());
        }
    }

    private static void mostrarSugerenciasError(Exception e) {
        System.err.println("\n🔍 Posibles soluciones:");

        if (e instanceof SQLException) {
            SQLException sqlEx = (SQLException) e;
            System.err.println("1. Código SQLState: " + sqlEx.getSQLState());
            System.err.println("2. Código de error: " + sqlEx.getErrorCode());

            if (sqlEx.getMessage().contains("No suitable driver")) {
                System.err.println("  - Asegúrate de tener mysql-connector-java en tus dependencias");
                System.err.println("  - Versión recomendada: 8.0.33 o superior");
            }
        } else if (e instanceof NullPointerException) {
            System.err.println("1. Verifica que todas las dependencias estén inicializadas");
        }
    }
}