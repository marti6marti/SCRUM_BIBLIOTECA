import domain.model.User;
import domain.implementDAO.UserCRUD;
import infrastructure.Conexion;
import java.sql.Connection;
import java.sql.SQLException;

public class TestUserDAO {
    public static void main(String[] args) {
        try {
            System.out.println("=== INICIO DE PRUEBAS ===");

            // 1. Probar conexión básica
            testConexionBasica();

            // 2. Probar operaciones CRUD
            testCRUDOperations();

            System.out.println("\n=== PRUEBAS COMPLETADAS CON ÉXITO ===");

        } catch (Exception e) {
            System.err.println("\n❌ Error durante las pruebas:");
            e.printStackTrace();
            mostrarSugerenciasError(e);
        }
    }

    private static void testConexionBasica() throws SQLException {
        System.out.println("\n--- Probando conexión básica ---");
        Conexion conexion = new Conexion();
        try (Connection conn = conexion.conectarMySQL()) {
            System.out.println("✅ Conexión exitosa a la base de datos");
            System.out.println("  - Producto: " + conn.getMetaData().getDatabaseProductName());
            System.out.println("  - Versión: " + conn.getMetaData().getDatabaseProductVersion());
            System.out.println("  - URL: " + conn.getMetaData().getURL());
        }
    }

    private static void testCRUDOperations() throws SQLException {
        System.out.println("\n--- Probando operaciones CRUD ---");
        UserCRUD userCRUD = new UserCRUD();

        // Crear usuario de prueba
        User testUser = new User();
        testUser.setName("test_user_" + System.currentTimeMillis());
        testUser.setPasswordHash("test_hash");
        testUser.setAdmin(false);

        System.out.println("Creando usuario...");
        int result = userCRUD.create(testUser);
        System.out.println("  - Resultado: " + result + " fila(s) afectada(s)");
        System.out.println("  - ID generado: " + testUser.getId());
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