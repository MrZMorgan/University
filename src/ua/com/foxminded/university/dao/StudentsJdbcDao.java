package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfacers.StudentsDao;
import ua.com.foxminded.university.dao.mappers.StudentMapper;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.Student;
import java.util.List;

@Repository
public class StudentsJdbcDao implements StudentsDao {

    public static final String CREATE = "INSERT INTO students (first_name, last_name, age, group_id) VALUES (?, ?, ?, ?)";
    public static final String READ = "SELECT * FROM courses WHERE id = ?";
    public static final String READ_STUDENTS_RELATED_TO_GROUP = "SELECT * FROM students WHERE group_id = ?";
    public static final String READ_STUDENTS_RELATED_TO_COURSE = "SELECT first_name, last_name, age, group_id FROM students " +
                                                                  "JOIN students_courses ON students.id = students_courses.student_id " +
                                                                  "WHERE course_id = ?";
    public static final String READ_ALL = "SELECT * FROM students";
    public static final String UPDATE = "UPDATE students SET fists_name=?, last_name = ?, age = ?, group_id = ? WHERE id=?";
    public static final String DELETE = "DELETE FROM students WHERE id=?";
    public final static String DAO_EXCEPTION_MESSAGE = "There is no student with this ID in the database";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentsJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Object[] data) {
        String firstName = (String) data[0];
        String lastName = (String) data[1];
        int age = (int) data[2];
        int groupId = (int) data[3];
        jdbcTemplate.update(CREATE, firstName, lastName, age, groupId);
    }

    public Student read(int studentId) {
        Student student = jdbcTemplate.query(READ, new Object[]{studentId}, new StudentMapper(jdbcTemplate))
                .stream()
                .findAny()
                .orElse(null);

        if (student == null) {
            try {
                throw new DAOException(DAO_EXCEPTION_MESSAGE);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

        return student;
    }

    public List<Student> readStudentsRelatedToGroup(int groupId) {
        return jdbcTemplate.query(READ_STUDENTS_RELATED_TO_GROUP,
                new Object[]{groupId}, new StudentMapper(jdbcTemplate));
    }

    public List<Student> readStudentsRelatedToCourse(int courseId) {
        return jdbcTemplate.query(READ_STUDENTS_RELATED_TO_COURSE, new Object[]{courseId}, new StudentMapper(jdbcTemplate));
    }

    public List<Student> read() {
        return jdbcTemplate.query(READ_ALL, new StudentMapper(jdbcTemplate));
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
