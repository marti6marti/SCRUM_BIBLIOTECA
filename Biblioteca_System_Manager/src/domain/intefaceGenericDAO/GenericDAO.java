package domain.intefaceGenericDAO;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {

    public int create(T entity) throws SQLException;
    public void delete(int id) throws SQLException;
    public T read(int id) throws SQLException;
    public void update(T entity) throws SQLException;
    public List<T> getAll() throws SQLException;
}