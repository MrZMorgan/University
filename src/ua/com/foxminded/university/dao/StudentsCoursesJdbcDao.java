package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.mappers.StudentsCoursesMapper;
import ua.com.foxminded.university.models.StudentCourse;

public class StudentsCoursesJdbcDao {

    private static final String INSERT = "INSERT INTO groups_courses (student_id, course_id) VALUES (?, ?)";
    private static final String READ = "SELECT * FROM students_courses " +
            "WHERE student_id = ? " +
            "AND course_id = ?";
    private static final String UPDATE_COURSE_ID = "UPDATE students_courses SET course_id=? WHERE course_id=?";
    private static final String UPDATE_STUDENT_ID = "UPDATE students_courses SET student_id=? WHERE studentId=?";
    private static final String DELETE_COURSE = "DELETE FROM students_courses WHERE course_id=?";
    private static final String DELETE_STUDENT = "DELETE FROM students_courses WHERE student_id=?";
    private static final String DELETE_STUDENT_FROM_COURSE = "DELETE FROM students_courses WHERE student_id=? AND course_id =?";

    private final JdbcTemplate jdbcTemplate;

    public StudentsCoursesJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(int studentId, int courseId) {
        jdbcTemplate.update(INSERT, studentId, courseId);
    }

    public StudentCourse read(int courseId, int studentId) {
        return jdbcTemplate.query(READ,
                new Object[]{studentId, courseId}, new StudentsCoursesMapper())
                .stream()
                .findAny()
                .orElse(null);
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
