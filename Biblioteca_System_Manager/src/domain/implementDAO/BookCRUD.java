package domain.implementDAO;

import domain.intefaceGenericDAO.GenericDAO;
import domain.model.Author;
import domain.model.Book;
import domain.model.Genre;
import infrastructure.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookCRUD implements GenericDAO<Book> {
    private final Conexion conexion;

    public BookCRUD() {
        this.conexion = new Conexion();
    }

    @Override
    public int create(Book book) throws SQLException {
        String sql = "INSERT INTO Book (id_Book, title, author_id, isAvailable) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, book.getId_Book());
            stmt.setString(2, book.getTitle());
            stmt.setInt(3, book.getAuthor().getId_Author());
            stmt.setBoolean(4, book.isAvailable());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int bookId = rs.getInt(1);
                    // Insert genres if any
                    if (book.getBookGenres() != null && !book.getBookGenres().isEmpty()) {
                        insertGenresForBook(bookId, book.getBookGenres(), conn);
                    }
                    return bookId;
                }
            }
        }
        return 0;
    }

    private void insertGenresForBook(int bookId, List<Genre> genres, Connection conn) throws SQLException {
        String sql = "INSERT INTO Book_Genre (book_id, genre_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Genre genre : genres) {
                stmt.setInt(1, bookId);
                stmt.setInt(2, genre.getId_Genere());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        // First delete from Book_Genre
        String deleteGenresSql = "DELETE FROM Book_Genre WHERE book_id = ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteGenresSql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }

        // Then delete the book
        String sql = "DELETE FROM Book WHERE id_Book = ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Book read(int id) throws SQLException {
        String sql = "SELECT b.*, a.id_Author, a.name as authorName FROM Book b " +
                "JOIN Author a ON b.author_id = a.id_Author " +
                "WHERE b.id_Book = ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Author author = new Author(
                            rs.getInt("author_id"),
                            rs.getString("authorName")
                    );
                    Book book = new Book(
                            rs.getInt("id_Book"),
                            rs.getString("title"),
                            author,
                            rs.getBoolean("isAvailable"),
                            new ArrayList<>()
                    );
                    // Load genres
                    book.setBookGenres(getGenresForBook(id, conn));
                    return book;
                }
            }
        }
        return null;
    }

    private ArrayList<Genre> getGenresForBook(int bookId, Connection conn) throws SQLException {
        ArrayList<Genre> genres = new ArrayList<>();
        String sql = "SELECT g.id_Genere, g.nameGenre FROM Genre g " +
                "JOIN Book_Genre bg ON g.id_Genere = bg.genre_id " +
                "WHERE bg.book_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    genres.add(new Genre(
                            rs.getString("nameGenre"),
                            rs.getInt("id_Genere")
                    ));
                }
            }
        }
        return genres;
    }

    @Override
    public void update(Book book) throws SQLException {
        String sql = "UPDATE Book SET title = ?, author_id = ?, isAvailable = ? WHERE id_Book = ?";
        try (Connection conn = conexion.getConnection()) {
            // Update book details
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, book.getTitle());
                stmt.setInt(2, book.getAuthor().getId_Author());
                stmt.setBoolean(3, book.isAvailable());
                stmt.setInt(4, book.getId_Book());
                stmt.executeUpdate();
            }

            // Update genres - first delete existing, then insert new
            String deleteGenresSql = "DELETE FROM Book_Genre WHERE book_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteGenresSql)) {
                stmt.setInt(1, book.getId_Book());
                stmt.executeUpdate();
            }

            if (book.getBookGenres() != null && !book.getBookGenres().isEmpty()) {
                insertGenresForBook(book.getId_Book(), book.getBookGenres(), conn);
            }
        }
    }

    @Override
    public List<Book> getAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, a.id_Author, a.name as authorName FROM Book b " +
                "JOIN Author a ON b.author_id = a.id_Author";
        try (Connection conn = conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Author author = new Author(
                        rs.getInt("author_id"),
                        rs.getString("authorName")
                );
                Book book = new Book(
                        rs.getInt("id_Book"),
                        rs.getString("title"),
                        author,
                        rs.getBoolean("isAvailable"),
                        new ArrayList<>()
                );
                // Load genres for each book
                book.setBookGenres(getGenresForBook(book.getId_Book(), conn));
                books.add(book);
            }
        }
        return books;
    }
}