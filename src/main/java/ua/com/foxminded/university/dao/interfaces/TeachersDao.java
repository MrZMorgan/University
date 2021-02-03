package ua.com.foxminded.university.dao.interfaces;

import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.Teacher;
import java.util.List;

public interface TeachersDao extends GenericDao<Teacher> {

    @Override
    void create(Teacher data);

    @Override
    Teacher read(int id) throws DAOException;

    @Override
    List<Teacher> read();

    @Override
    void update(int id, Teacher teacherForQuery);

    @Override
    void delete(int courseId);
}