package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.mappers.TeacherMapper;
import ua.com.foxminded.university.models.Teacher;

import java.util.List;

public class TeacherJdbc {

    private final JdbcTemplate jdbcTemplate;

    public TeacherJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(String firstName, String lastName, int age) {
        jdbcTemplate.update("INSERT INTO teachers (firts_name, last_name, age) " +
                "VALUES (?, ?, ?)", firstName, lastName, age);
    }

    public Teacher read(int studentId) {
        return jdbcTemplate.query("SELECT * FROM teacher WHERE id = ?", new Object[]{studentId}, new TeacherMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public List<Teacher> read() {
        return jdbcTemplate.query("SELECT * FROM teachers", new TeacherMapper());
    }

    public void update(int id, Teacher teacherForQuery) {
        jdbcTemplate.update("UPDATE students SET firts_name=?, last_name = ?, age = ? WHERE id=?",
                teacherForQuery.getFirstName(),
                teacherForQuery.getLastName(),
                teacherForQuery.getAge(),
                id);
    }

    public void delete(int teacherId) {
        jdbcTemplate.update("DELETE FROM students WHERE id=?", teacherId);
    }
}
