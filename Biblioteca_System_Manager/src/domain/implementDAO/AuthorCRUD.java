package domain.implementDAO;

import domain.intefaceGenericDAO.GenericDAO;
import domain.model.Author;
import infrastructure.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorCRUD implements GenericDAO<Author> {
    private final Conexion conexion;

    public AuthorCRUD() {
        this.conexion = new Conexion();
    }

    @Override
    public int create(Author author) throws SQLException {
        String sql = "INSERT INTO Author (name) VALUES (?)";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, author.getName());
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
        String sql = "DELETE FROM Author WHERE id_Author = ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Author read(int id) throws SQLException {
        String sql = "SELECT * FROM Author WHERE id_Author = ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Author(
                            rs.getInt("id_Author"),
                            rs.getString("name")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void update(Author author) throws SQLException {
        String sql = "UPDATE Author SET name = ? WHERE id_Author = ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, author.getName());
            stmt.setInt(2, author.getId_Author());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Author> getAll() throws SQLException {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM Author";
        try (Connection conn = conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                authors.add(new Author(
                        rs.getInt("id_Author"),
                        rs.getString("name")
                ));
            }
        }
        return authors;
    }
}
