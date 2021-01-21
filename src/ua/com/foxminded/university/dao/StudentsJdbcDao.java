package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfaces.StudentsDao;
import ua.com.foxminded.university.dao.mappers.StudentMapper;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.Student;
import java.util.List;

@Repository
public class StudentsJdbcDao implements StudentsDao {

    public static final String CREATE = "INSERT INTO students (first_name, last_name, age, group_id) VALUES (?, ?, ?, ?)";
    public static final String READ = "SELECT * FROM students WHERE id = ?";
    public static final String READ_STUDENTS_RELATED_TO_GROUP = "SELECT * FROM students WHERE group_id = ?";
    public static final String READ_STUDENTS_RELATED_TO_COURSE = "SELECT * FROM students " +
                                                                 "JOIN students_courses ON students.id = students_courses.student_id " +
                                                                 "WHERE course_id = ?";
    public static final String READ_ALL = "SELECT * FROM students";
    public static final String DELETE_STUDENT_FROM_GROUP = "UPDATE students SET group_id = null WHERE group_id = ?";
    public static final String UPDATE = "UPDATE students SET first_name=?, last_name = ?, age = ?, group_id = ? WHERE id=?";
    public static final String DELETE = "DELETE FROM students WHERE id=?";
    public final static String DAO_EXCEPTION_MESSAGE = "There is no student with this ID in the database";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentsJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Student data) {
        String firstName = data.getFirstName();
        String lastName = data.getLastName();
        int age = data.getAge();
        int groupId = data.getGroup().getGroupId();
        jdbcTemplate.update(CREATE, firstName, lastName, age, groupId);
    }

    @Override
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

    @Override
    public List<Student> read() {
        return jdbcTemplate.query(READ_ALL, new StudentMapper(jdbcTemplate));
    }

    @Override
    public void update(int id, Student studentForQuery) {
        new StudentsCoursesJdbcDao(jdbcTemplate).updateStudentId(id, studentForQuery.getId());
        jdbcTemplate.update(UPDATE,
                studentForQuery.getFirstName(),
                studentForQuery.getLastName(),
                studentForQuery.getAge(),
                studentForQuery.getGroup().getGroupId(),
                id);
    }

    public void deleteStudentFromGroup(int groupId) {
        jdbcTemplate.update(DELETE_STUDENT_FROM_GROUP, groupId);
    }



    @Override
    public void delete(int studentId) {
        jdbcTemplate.update(DELETE, studentId);
    }
}
