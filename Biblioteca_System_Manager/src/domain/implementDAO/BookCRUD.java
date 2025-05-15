package domain.implementDAO;

import domain.intefaceGenericDAO.GenericDAO;

import java.sql.SQLException;
import java.util.List;

public class BookCRUD implements GenericDAO {
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
