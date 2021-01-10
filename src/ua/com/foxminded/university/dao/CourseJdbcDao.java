package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.mappers.CourseMapper;
import ua.com.foxminded.university.dao.mappers.StudentMapper;
import ua.com.foxminded.university.models.Course;
import ua.com.foxminded.university.models.Student;

import java.util.List;

public class CourseJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    public CourseJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(String courseName) {
        jdbcTemplate.update("INSERT INTO courses (name) VALUES (?)", courseName);
    }

    public Course read(int courseId) {
        return jdbcTemplate.query("SELECT * FROM courses WHERE id = ?", new Object[]{courseId}, new CourseMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public List<Course> read() {
        return jdbcTemplate.query("SELECT * FROM courses", new CourseMapper());
    }

    public void update(int id, Course courseForQuery) {
        jdbcTemplate.update("UPDATE course SET name=?, teacher_id=? WHERE id=?",
                courseForQuery.getCourseName(),
                courseForQuery.getTeacher().getId(),
                id);
    }

    public void delete(int courseId) {
        jdbcTemplate.update("DELETE FROM course WHERE id=?", courseId);
    }
}
