package ua.com.foxminded.university.dao.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.GroupsJdbcDao;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.dao.TeachersJdbcDao;
import ua.com.foxminded.university.models.Course;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.models.Student;
import ua.com.foxminded.university.models.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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

        int courseId = rs.getInt("id");
        String courseName = rs.getString("name");
        int teacherId = rs.getInt("teacher_id");
        Teacher teacher = teachersJdbcDao.read(teacherId);
        List<Group> groups = new LinkedList<>();
        List<Student> students = new LinkedList<>();

        return new Course(courseId, courseName, teacher, groups, students);
    }
}
