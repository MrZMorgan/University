package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.mappers.StudentsCoursesMapper;
import ua.com.foxminded.university.models.StudentCourse;

public class StudentsCoursesJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    public StudentsCoursesJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(int studentId, int courseId) {
        jdbcTemplate.update("INSERT INTO groups_courses (student_id, course_id) VALUES (?, ?)", studentId, courseId);
    }

    public StudentCourse read(int courseId, int studentId) {
        return jdbcTemplate.query(
                "SELECT * FROM students_courses " +
                        "WHERE student_id = ? " +
                        "AND course_id = ?",
                new Object[]{studentId, courseId}, new StudentsCoursesMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public void updateCourseId(int courseId, int updatedId) {
        jdbcTemplate.update("UPDATE students_courses SET course_id=? WHERE course_id=?",
                updatedId,
                courseId);
    }

    public void updateStudentId(int studentId, int updatedId) {
        jdbcTemplate.update("UPDATE students_courses SET student_id=? WHERE studentId=?",
                updatedId,
                studentId);
    }

    public void deleteCourse(int courseId) {
        jdbcTemplate.update("DELETE FROM students_courses WHERE course_id=?", courseId);
    }

    public void deleteStudent(int studentId) {
        jdbcTemplate.update("DELETE FROM students_courses WHERE student_id=?", studentId);
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        jdbcTemplate.update("DELETE FROM students_courses WHERE studentId=? AND coures_id =?", studentId, courseId);
    }
}
