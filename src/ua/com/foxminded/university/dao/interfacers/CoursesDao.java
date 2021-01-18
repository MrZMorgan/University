package ua.com.foxminded.university.dao.interfacers;

import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.Course;
import java.util.List;

public interface CoursesDao extends GenericDao<Course>{

    @Override
    void create(Course data);

    @Override
    Course read(int id) throws DAOException;

    @Override
    List<Course> read();

    @Override
    void update(int id, Course courseForQuery);

    @Override
    void delete(int courseId);
}
