package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.GroupsJdbcDao;
import ua.com.foxminded.university.dao.StudentJdbcDao;
import ua.com.foxminded.university.dao.TeacherJdbcDao;
import ua.com.foxminded.university.models.Course;
import ua.com.foxminded.university.models.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements RowMapper<Course> {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final TeacherJdbcDao teacherJdbcDao = new TeacherJdbcDao(jdbcTemplate);
    private final StudentJdbcDao studentJdbcDao = new StudentJdbcDao(jdbcTemplate);
    private final GroupsJdbcDao groupsJdbcDao = new GroupsJdbcDao(jdbcTemplate);

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Course(
                rs.getInt("id"),
                rs.getString("name"),
                teacherJdbcDao.read(rs.getInt("teacher_id")),
                groupsJdbcDao.readGroupsRelatedToCourse(rs.getInt("id")),
                studentJdbcDao.readStudentsRelatedToCourse(rs.getInt("id"))
        );
    }
}
