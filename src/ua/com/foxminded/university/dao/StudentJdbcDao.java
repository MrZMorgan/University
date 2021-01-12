package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.mappers.GroupMapper;
import ua.com.foxminded.university.dao.mappers.StudentMapper;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.models.Student;

import java.util.List;

public class StudentJdbcDao {

    private static final String CREATE = "INSERT INTO students (first_name, last_name, age, group_id) VALUES (?, ?, ?, ?)";
    private static final String READ = "SELECT * FROM courses WHERE id = ?";
    private static final String READ_STUDENTS_RELATED_TO_GROUP = "SELECT * FROM students WHERE group_id = ?";
    private static final String READ_STUDENTS_RELATED_TO_COURSE = "SELECT first_name, last_name, age, group_id FROM students " +
                                                                  "JOIN students_courses; ON students.id = students_courses.id " +
                                                                  "WHERE course_id = ?";
    private static final String READ_ALL = "SELECT * FROM students";
    private static final String UPDATE = "UPDATE students SET fists_name=?, last_name = ?, age = ?, group_id = ? WHERE id=?";
    private static final String DELETE = "DELETE FROM students WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    public StudentJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(String firstName, String lastName, int age, int groupId) {
        jdbcTemplate.update(CREATE, firstName, lastName, age, groupId);
    }

    public Student read(int studentId) {
        return jdbcTemplate.query(READ, new Object[]{studentId}, new StudentMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public List<Student> readStudentsRelatedToGroup(int groupId) {
        return jdbcTemplate.query(READ_STUDENTS_RELATED_TO_GROUP,
                new Object[]{groupId}, new StudentMapper());
    }

    public List<Student> readStudentsRelatedToCourse(int courseId) {
        return jdbcTemplate.query(READ_STUDENTS_RELATED_TO_COURSE, new Object[]{courseId}, new StudentMapper());
    }

    public List<Student> read() {
        return jdbcTemplate.query(READ_ALL, new StudentMapper());
    }

    public void update(int id, Student studentForQuery) {
        jdbcTemplate.update(UPDATE,
                studentForQuery.getFirstName(),
                studentForQuery.getLastName(),
                studentForQuery.getAge(),
                studentForQuery.getGroup().getGroupId(),
                id);
    }

    public void delete(int studentId) {
        jdbcTemplate.update(DELETE, studentId);
    }
}
