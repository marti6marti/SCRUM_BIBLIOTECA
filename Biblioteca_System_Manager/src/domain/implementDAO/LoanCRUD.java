package domain.implementDAO;

import domain.intefaceGenericDAO.GenericDAO;
import domain.model.Book;
import domain.model.Loan;
import domain.model.User;
import infrastructure.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanCRUD implements GenericDAO<Loan> {
    private final Conexion conexion;

    public LoanCRUD() {
        this.conexion = new Conexion();
    }

    @Override
    public int create(Loan loan) throws SQLException {
        String sql = "INSERT INTO Loan (id_Loan, user_id, book_id, loanDate, returnDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, loan.getId_Loan());
            stmt.setInt(2, loan.getUser().getId_User());
            stmt.setInt(3, loan.getBook().getId_Book());
            stmt.setDate(4, new java.sql.Date(loan.getLoanDate().getTime()));
            stmt.setDate(5, loan.getReturnDate() != null ? new java.sql.Date(loan.getReturnDate().getTime()) : null);
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
        String sql = "DELETE FROM Loan WHERE id_Loan = ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Loan read(int id) throws SQLException {
        String sql = "SELECT * FROM Loan WHERE id_Loan = ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserCRUD userCRUD = new UserCRUD();
                    BookCRUD bookCRUD = new BookCRUD();
                    User user = userCRUD.read(rs.getInt("user_id"));
                    Book book = bookCRUD.read(rs.getInt("book_id"));
                    return new Loan(
                            rs.getInt("id_Loan"),
                            user,
                            book,
                            rs.getDate("loanDate"),
                            rs.getDate("returnDate")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void update(Loan loan) throws SQLException {
        String sql = "UPDATE Loan SET user_id = ?, book_id = ?, loanDate = ?, returnDate = ? WHERE id_Loan = ?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, loan.getUser().getId_User());
            stmt.setInt(2, loan.getBook().getId_Book());
            stmt.setDate(3, new java.sql.Date(loan.getLoanDate().getTime()));
            stmt.setDate(4, loan.getReturnDate() != null ? new java.sql.Date(loan.getReturnDate().getTime()) : null);
            stmt.setInt(5, loan.getId_Loan());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Loan> getAll() throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loan";
        try (Connection conn = conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            UserCRUD userCRUD = new UserCRUD();
            BookCRUD bookCRUD = new BookCRUD();
            while (rs.next()) {
                User user = userCRUD.read(rs.getInt("user_id"));
                Book book = bookCRUD.read(rs.getInt("book_id"));
                loans.add(new Loan(
                        rs.getInt("id_Loan"),
                        user,
                        book,
                        rs.getDate("loanDate"),
                        rs.getDate("returnDate")
                ));
            }
        }
        return loans;
    }
}