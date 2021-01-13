package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.mappers.StudentCourseMapper;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.StudentCourse;

@Repository
public class StudentsCoursesJdbcDao {

    public static final String CREATE = "INSERT INTO groups_courses (student_id, course_id) VALUES (?, ?)";
    public static final String READ = "SELECT * FROM students_courses " +
            "WHERE student_id = ? " +
            "AND course_id = ?";
    public static final String UPDATE_COURSE_ID = "UPDATE students_courses SET course_id=? WHERE course_id=?";
    public static final String UPDATE_STUDENT_ID = "UPDATE students_courses SET student_id=? WHERE studentId=?";
    public static final String DELETE_COURSE = "DELETE FROM students_courses WHERE course_id=?";
    public static final String DELETE_STUDENT = "DELETE FROM students_courses WHERE student_id=?";
    public static final String DELETE_STUDENT_FROM_COURSE = "DELETE FROM students_courses WHERE student_id=? AND course_id =?";
    public final static String DAO_EXCEPTION_MESSAGE = "There is no group-course with this ID in the database";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void create(int studentId, int courseId) {
        jdbcTemplate.update(CREATE, studentId, courseId);
    }

    public StudentCourse read(int courseId, int studentId) {
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

    public void deleteCourse(int courseId) {
        jdbcTemplate.update(DELETE_COURSE, courseId);
    }

    public void deleteStudent(int studentId) {
        jdbcTemplate.update(DELETE_STUDENT, studentId);
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        jdbcTemplate.update(DELETE_STUDENT_FROM_COURSE, studentId, courseId);
    }
}
