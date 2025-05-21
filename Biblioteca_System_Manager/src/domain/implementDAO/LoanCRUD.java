package domain.implementDAO;

import domain.intefaceGenericDAO.GenericDAO;
import domain.model.Book;
import domain.model.Loan;
import domain.model.User;
import infrastructure.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanCRUD implements GenericDAO {
    private final Conexion conexion;

    public LoanCRUD() {
        this.conexion = new Conexion();
    }


    @Override
    public int create(Object entity) throws SQLException {
        return 0;
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public Object read(int id) throws SQLException {
        return null;
    }

    @Override
    public void update(Object entity) throws SQLException {

    }

    @Override
    public List getAll() throws SQLException {
        return List.of();
    }
}