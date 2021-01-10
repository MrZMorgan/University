package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.CourseJdbcDao;
import ua.com.foxminded.university.dao.GroupsJdbcDao;
import ua.com.foxminded.university.models.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final GroupsJdbcDao groupsJdbcDao = new GroupsJdbcDao(jdbcTemplate);
    private final CourseJdbcDao courseJdbcDao = new CourseJdbcDao(jdbcTemplate);

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getInt("age"),
                groupsJdbcDao.read(rs.getInt("group_id")),
                courseJdbcDao.readCoursesRelatedToStudent(rs.getInt("id"))
        );
    }
}
