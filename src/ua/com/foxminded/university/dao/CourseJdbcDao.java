package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.mappers.CourseMapper;
import ua.com.foxminded.university.models.Course;

import java.util.List;

public class CourseJdbcDao {

    private static final String INSERT_INTO = "INSERT INTO courses (name) VALUES (?)";
    private static final String READ = "SELECT * FROM courses WHERE id = ?";
    private static final String READ_ALL = "SELECT * FROM courses";
    private static final String READ_ALL_BY_TEACHER_ID = "SELECT * FROM courses WHERE teacher_id = ?";
    private static final String READ_COURSES_RELATED_TO_TEACHER = "SELECT name, teacher_id FROM courses " +
                                                                    "JOIN students_courses ON courses.id = students_courses.id " +
                                                                    "WHERE student_id = ?";
    private final static String UPDATE_COURSE = "UPDATE course SET name=?, teacher_id=? WHERE id=?";
    private final static String DELETE_COURSE_BY_ID = "DELETE FROM course WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    public CourseJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(String courseName) {
        jdbcTemplate.update(INSERT_INTO, courseName);
    }

    public Course read(int courseId) {
        return jdbcTemplate.query(READ, new Object[]{courseId}, new CourseMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public List<Course> read() {
        return jdbcTemplate.query(READ_ALL, new CourseMapper());
    }

    public List<Course> readCoursesRelatedToTeacher(int teacherId) {
        return jdbcTemplate.query(READ_ALL_BY_TEACHER_ID,
                new Object[]{teacherId}, new CourseMapper());
    }

    public List<Course> readCoursesRelatedToStudent(int studentId) {
        return jdbcTemplate.query(
                READ_COURSES_RELATED_TO_TEACHER,
                new Object[]{studentId}, new CourseMapper());
    }

    public void update(int id, Course courseForQuery) {
        jdbcTemplate.update(UPDATE_COURSE,
                courseForQuery.getCourseName(),
                courseForQuery.getTeacher().getId(),
                id);
    }

    public void delete(int courseId) {
        jdbcTemplate.update(DELETE_COURSE_BY_ID, courseId);
    }
}
