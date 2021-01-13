package ua.com.foxminded.university.dao.interfacers;

import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.Teacher;
import java.util.List;

public interface TeachersDao extends GenericDao<Teacher> {

    @Override
    void create(Object[] data);

    @Override
    Teacher read(int id) throws DAOException;

    @Override
    List<Teacher> read();

    @Override
    void update(int id, Teacher teacherForQuery);

    @Override
    void delete(int courseId);
}
