package domain.implementDAO;

import domain.intefaceGenericDAO.GenericDAO;
import domain.model.Author;
import domain.model.Book;
import domain.model.Genre;
import infrastructure.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookCRUD implements GenericDAO {
    private final Conexion conexion;

    public BookCRUD() {
        this.conexion = new Conexion();
    }

    @Override
    public int create(Object entity) throws SQLException {
        if (!(entity instanceof Book)) {
            throw new IllegalArgumentException("Expected a Book object");
        }

        Book book = (Book) entity;

        String query = "INSERT INTO Book(title, authorId, genre, isAvailable) VALUES (?, ?, ?, ?)";

        try (Connection conn = conexion.conectarMySQL();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getAuthor().getId());
            ps.setInt(3, book.getGenre().getGenereid());
            ps.setBoolean(4, book.isAvailable());

            int affectedRows = ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                }
            }

            return affectedRows;
        }
    }


    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Book WHERE id = ?";
        try (Connection conn = conexion.conectarMySQL();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Book read(int id) throws SQLException {
        String query = "SELECT b.id, b.title, b.isAvailable, "
                + "a.id as author_id, a.name as author_name, "
                + "g.genereid as genre_id, g.nameGenre as genre_name "
                + "FROM Book b "
                + "JOIN Author a ON b.author_id = a.id "
                + "JOIN Genre g ON b.genre_id = g.genereid "
                + "WHERE b.id = ?";

        try (Connection conn = conexion.conectarMySQL();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            new Author(rs.getInt("author_id"), rs.getString("author_name")),
                            new Genre(rs.getString("genre_name"), rs.getInt("genre_id"))
                    );
                    book.setAvailable(rs.getBoolean("isAvailable"));
                    return book;
                }
            }
        }
        return null;
    }

    @Override
    public void update(Object entity) throws SQLException {
        if (!(entity instanceof Book)) {
            throw new IllegalArgumentException("El objeto no es una instancia de Book");
        }

        Book book = (Book) entity;
        String query = "UPDATE Book SET title = ?, author_id = ?, genre_id = ?, isAvailable = ? WHERE id = ?";

        try (Connection conn = conexion.conectarMySQL();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getAuthor().getId());
            ps.setInt(3, book.getGenre().getGenereid());
            ps.setBoolean(4, book.isAvailable());
            ps.setInt(5, book.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<Object> getAll() throws SQLException {
        String query = "SELECT b.id, b.title, b.isAvailable, "
                + "a.id as author_id, a.name as author_name, "
                + "g.genereid as genre_id, g.nameGenre as genre_name "
                + "FROM Book b "
                + "JOIN Author a ON b.author_id = a.id "
                + "JOIN Genre g ON b.genre_id = g.genereid";

        List<Object> books = new ArrayList<>();

        try (Connection conn = conexion.conectarMySQL();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        new Author(rs.getInt("author_id"), rs.getString("author_name")),
                        new Genre(rs.getString("genre_name"), rs.getInt("genre_id"))
                );
                book.setAvailable(rs.getBoolean("isAvailable"));
                books.add(book);
            }
        }

        return books;
    }

    // Això és una funció extra per comprovar la disponibilitat
    public boolean isBookAvailable(int id) throws SQLException {
        String query = "SELECT isAvailable FROM Book WHERE id = ?";
        try (Connection conn = conexion.conectarMySQL();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("isAvailable");
                }
            }
        }
        return false;
    }
}
