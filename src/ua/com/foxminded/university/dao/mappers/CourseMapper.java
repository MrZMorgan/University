package ua.com.foxminded.university.dao.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.GroupsJdbcDao;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.dao.TeachersJdbcDao;
import ua.com.foxminded.university.models.Course;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements RowMapper<Course> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        TeachersJdbcDao teachersJdbcDao = new TeachersJdbcDao(jdbcTemplate);
        StudentsJdbcDao studentsJdbcDao = new StudentsJdbcDao(jdbcTemplate);
        GroupsJdbcDao groupsJdbcDao = new GroupsJdbcDao(jdbcTemplate);

        return new Course(
                rs.getInt("id"),
                rs.getString("name"),
                teachersJdbcDao.readTeacherRelatedToCourse(rs.getInt("id")),
                groupsJdbcDao.readGroupsRelatedToCourse(rs.getInt("id")),
                studentsJdbcDao.readStudentsRelatedToCourse(rs.getInt("id"))
        );
    }
}
