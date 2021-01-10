package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.mappers.StudentMapper;
import ua.com.foxminded.university.models.Student;

import java.util.List;

public class StudentJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    public StudentJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(String firstName, String lastName, int age, int groupId) {
        jdbcTemplate.update("INSERT INTO students (firts_name, last_name, age, group_id) " +
                "VALUES (?, ?, ?, ?)", firstName, lastName, age, groupId);
    }

    public Student read(int studentId) {
        return jdbcTemplate.query("SELECT * FROM courses WHERE id = ?", new Object[]{studentId}, new StudentMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public List<Student> read() {
        return jdbcTemplate.query("SELECT * FROM students", new StudentMapper());
    }

    public void update(int id, Student studentForQuery) {
        jdbcTemplate.update("UPDATE students SET firts_name=?, last_name = ?, age = ?, group_id = ? WHERE id=?",
                studentForQuery.getFirstName(),
                studentForQuery.getLastName(),
                studentForQuery.getAge(),
                studentForQuery.getGroup().getGroupId(),
                id);
    }

    public void delete(int studentId) {
        jdbcTemplate.update("DELETE FROM students WHERE id=?", studentId);
    }
}
