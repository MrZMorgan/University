package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.GroupsJdbcDao;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.dao.TeachersJdbcDao;
import ua.com.foxminded.university.models.Course;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements RowMapper<Course> {

    private final TeachersJdbcDao teachersJdbcDao = new TeachersJdbcDao();
    private final StudentsJdbcDao studentsJdbcDao = new StudentsJdbcDao();
    private final GroupsJdbcDao groupsJdbcDao = new GroupsJdbcDao();

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Course(
                rs.getInt("id"),
                rs.getString("name"),
                teachersJdbcDao.read(rs.getInt("teacher_id")),
                groupsJdbcDao.readGroupsRelatedToCourse(rs.getInt("id")),
                studentsJdbcDao.readStudentsRelatedToCourse(rs.getInt("id"))
        );
    }
}
