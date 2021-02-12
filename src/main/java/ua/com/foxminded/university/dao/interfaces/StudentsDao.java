package ua.com.foxminded.university.dao.interfaces;

import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.entities.Student;
import java.util.List;

public interface StudentsDao extends GenericDao<Student> {

    @Override
    void create(Student data);

    @Override
    Student read(int id) throws DAOException;

    @Override
    List<Student> read();

    @Override
    void update(int id, Student studentForQuery);

    @Override
    void delete(int courseId);
}
