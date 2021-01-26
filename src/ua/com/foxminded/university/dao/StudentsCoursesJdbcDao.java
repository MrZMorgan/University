package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.mappers.StudentCourseMapper;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.StudentCourse;

import java.util.List;

@Repository
public class StudentsCoursesJdbcDao {

    public static final String CREATE = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
    public static final String READ = "SELECT * FROM students_courses " +
                                      "WHERE student_id = ? " +
                                      "AND course_id = ?";
    public static final String READ_ALL = "SELECT * FROM students_courses";
    public static final String UPDATE_COURSE_ID = "UPDATE students_courses SET course_id=? WHERE course_id=?";
    public static final String UPDATE_STUDENT_ID = "UPDATE students_courses SET student_id=? WHERE student_id=?";
    public static final String DELETE_STUDENT = "DELETE FROM students_courses WHERE student_id=?";
    public static final String DELETE_STUDENT_FROM_COURSE = "DELETE FROM students_courses WHERE student_id=? AND course_id =?";
    public final static String DAO_EXCEPTION_MESSAGE = "There is no group-course with this ID in the database";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentsCoursesJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(int studentId, int courseId) {
        jdbcTemplate.update(CREATE, studentId, courseId);
    }

    public StudentCourse read(int studentId, int courseId) {
        StudentCourse studentCourse = jdbcTemplate.query(READ,
                new Object[]{studentId, courseId}, new StudentCourseMapper())
                .stream()
                .findAny()
                .orElse(null);

        if (studentCourse == null) {
            try {
                throw new DAOException(DAO_EXCEPTION_MESSAGE);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

        return studentCourse;
    }

    public List<StudentCourse> read() {
        return jdbcTemplate.query(READ_ALL, new StudentCourseMapper());
    }

    public void updateCourseId(int courseId, int updatedId) {
        jdbcTemplate.update(UPDATE_COURSE_ID,
                updatedId,
                courseId);
    }

    public void updateStudentId(int studentId, int updatedId) {
        jdbcTemplate.update(UPDATE_STUDENT_ID,
                updatedId,
                studentId);
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        jdbcTemplate.update(DELETE_STUDENT_FROM_COURSE, studentId, courseId);
    }
}
