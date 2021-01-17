package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfacers.TeachersDao;
import ua.com.foxminded.university.dao.mappers.TeacherMapper;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.Teacher;
import java.util.List;

@Repository
public class TeachersJdbcDao implements TeachersDao {

    public static final String CREATE = "INSERT INTO teachers (first_name, last_name, age) VALUES (?, ?, ?)";
    public static final String READ = "SELECT * FROM teacher WHERE id = ?";
    public static final String READ_ALL = "SELECT * FROM teachers";
    public static final String UPDATE = "UPDATE students SET first_name = ?, last_name = ?, age = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM students WHERE id=?";
    public final static String DAO_EXCEPTION_MESSAGE = "There is no teacher with this ID in the database";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeachersJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Object[] data) {
        String firstName = (String) data[0];
        String lastName = (String) data[1];
        int age = (int) data[2];
        jdbcTemplate.update(CREATE, firstName, lastName, age);
    }

    public Teacher read(int studentId) {
        Teacher teacher = jdbcTemplate.query(READ, new Object[]{studentId}, new TeacherMapper(jdbcTemplate))
                .stream()
                .findAny()
                .orElse(null);

        if (teacher == null) {
            try {
                throw new DAOException(DAO_EXCEPTION_MESSAGE);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

        return teacher;
    }

    public List<Teacher> read() {
        return jdbcTemplate.query(READ_ALL, new TeacherMapper(jdbcTemplate));
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
