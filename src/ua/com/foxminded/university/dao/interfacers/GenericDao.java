package ua.com.foxminded.university.dao.interfacers;

import ua.com.foxminded.university.exceptions.DAOException;

import java.util.List;

public interface GenericDao<T> {

    void create(Object[] data);
    T read(int id) throws DAOException;
    List<T> read();
    void update(int id, T objectForQuery);
    void delete(int courseId);
}
