package domain.implementDAO;

import domain.intefaceGenericDAO.GenericDAO;
import domain.model.User;
import infrastructure.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserCRUD implements GenericDAO<User> {
    private final Conexion conexion;

    public UserCRUD() {
        this.conexion = new Conexion();
    }

    @Override
    public int create(User user) throws SQLException {
        String sql = "INSERT INTO User (id_User, name, passwordHash, isAdmin) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, user.getId_User());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getPasswordHash());
            stmt.setBoolean(4, user.isAdmin());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM User WHERE id_User = ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public User read(int id) throws SQLException {
        String sql = "SELECT * FROM User WHERE id_User = ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id_User"),
                            rs.getString("name"),
                            rs.getString("passwordHash"),
                            rs.getBoolean("isAdmin"),
                            new ArrayList<>()
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE User SET name = ?, passwordHash = ?, isAdmin = ? WHERE id_User = ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPasswordHash());
            stmt.setBoolean(3, user.isAdmin());
            stmt.setInt(4, user.getId_User());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Connection conn = conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id_User"),
                        rs.getString("name"),
                        rs.getString("passwordHash"),
                        rs.getBoolean("isAdmin"),
                        new ArrayList<>()
                ));
            }
        }
        return users;
    }
}