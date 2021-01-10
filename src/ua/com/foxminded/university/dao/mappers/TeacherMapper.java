package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.CourseJdbcDao;
import ua.com.foxminded.university.models.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherMapper implements RowMapper<Teacher> {

    private final CourseJdbcDao courseJdbcDao = new CourseJdbcDao(new JdbcTemplate());

    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Teacher(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getInt("age"),
                courseJdbcDao.readCoursesRelatedToTeacher(rs.getInt("id"))
        );
    }
}
