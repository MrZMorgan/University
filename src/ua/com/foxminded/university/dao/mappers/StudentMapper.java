package ua.com.foxminded.university.dao.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.dao.GroupsJdbcDao;
import ua.com.foxminded.university.models.Student;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        GroupsJdbcDao groupsJdbcDao = new GroupsJdbcDao(jdbcTemplate);
        CoursesJdbcDao coursesJdbcDao = new CoursesJdbcDao(jdbcTemplate);

        return new Student(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getInt("age"),
                groupsJdbcDao.read(rs.getInt("id")),
                coursesJdbcDao.readCoursesRelatedToStudent(rs.getInt("student_id"))
        );
    }
}
