package ua.com.foxminded.university.dao.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.dao.GroupsJdbcDao;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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

        int studentId = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        int age = rs.getInt("age");
        Group group = groupsJdbcDao.readGroupByStudentId(studentId);
        List<Course> courses = new LinkedList<>();

        return new Student(studentId, firstName, lastName, age, group, courses);
    }
}
