package ua.com.foxminded.university.dao.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.models.Course;
import ua.com.foxminded.university.models.Teacher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeacherMapper implements RowMapper<Teacher> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
        CoursesJdbcDao coursesJdbcDao = new CoursesJdbcDao(jdbcTemplate);

        int teacherId = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        int age = rs.getInt("age");
        List<Course> courses = coursesJdbcDao.readCoursesRelatedToTeacher(teacherId);

        return new Teacher(teacherId, firstName, lastName, age, courses);
    }
}
