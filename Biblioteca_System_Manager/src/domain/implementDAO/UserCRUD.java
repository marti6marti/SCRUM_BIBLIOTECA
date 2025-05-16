package domain.implementDAO;

import domain.model.User;
import infrastructure.Conexion;
import java.sql.*;
import java.util.List;

public class UserCRUD {
    private final Conexion conexion;

    public UserCRUD() {
        this.conexion = new Conexion();
    }

    public int create(User user) throws SQLException {
        String query = "INSERT INTO User(name, passwordHash, isAdmin) VALUES (?, ?, ?)";
        try (Connection conn = conexion.conectarMySQL();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getPasswordHash());
            ps.setBoolean(3, user.isAdmin());

            int affectedRows = ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }

            return affectedRows;
        }
    }

}