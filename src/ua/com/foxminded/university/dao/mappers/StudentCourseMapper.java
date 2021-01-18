package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.models.StudentCourse;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentCourseMapper implements RowMapper<StudentCourse> {

    @Override
    public StudentCourse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StudentCourse(
                rs.getInt("student_id"),
                rs.getInt("course_id")
        );
    }

}
