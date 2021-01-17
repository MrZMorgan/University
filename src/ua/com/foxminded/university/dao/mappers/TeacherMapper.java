package ua.com.foxminded.university.dao.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.models.Teacher;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherMapper implements RowMapper<Teacher> {

    @Autowired
    private CoursesJdbcDao coursesJdbcDao;

    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Teacher(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getInt("age"),
                coursesJdbcDao.readCoursesRelatedToTeacher(rs.getInt("id"))
        );
    }
}
