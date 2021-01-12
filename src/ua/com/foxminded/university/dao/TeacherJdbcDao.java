package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.mappers.TeacherMapper;
import ua.com.foxminded.university.models.Teacher;

import java.util.List;

public class TeacherJdbcDao {

    private static final String CREATE = "INSERT INTO teachers (first_name, last_name, age) VALUES (?, ?, ?)";
    private static final String READ = "SELECT * FROM teacher WHERE id = ?";
    private static final String READ_ALL = "SELECT * FROM teachers";
    private static final String UPDATE = "UPDATE students SET first_name = ?, last_name = ?, age = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM students WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    public TeacherJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(String firstName, String lastName, int age) {
        jdbcTemplate.update(CREATE, firstName, lastName, age);
    }

    public Teacher read(int studentId) {
        return jdbcTemplate.query(READ, new Object[]{studentId}, new TeacherMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public List<Teacher> read() {
        return jdbcTemplate.query(READ_ALL, new TeacherMapper());
    }

    public void update(int id, Teacher teacherForQuery) {
        jdbcTemplate.update(UPDATE,
                teacherForQuery.getFirstName(),
                teacherForQuery.getLastName(),
                teacherForQuery.getAge(),
                id);
    }

    public void delete(int teacherId) {
        jdbcTemplate.update(DELETE, teacherId);
    }
}
