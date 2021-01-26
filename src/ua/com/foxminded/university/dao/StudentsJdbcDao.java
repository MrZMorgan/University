package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfaces.StudentsDao;
import ua.com.foxminded.university.dao.mappers.StudentCourseMapper;
import ua.com.foxminded.university.dao.mappers.StudentMapper;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.Student;
import ua.com.foxminded.university.models.StudentCourse;

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
    public static final String DELETE_STUDENTS_FROM_GROUP = "UPDATE students SET group_id = null WHERE group_id = ?";
    public static final String UPDATE = "UPDATE students SET first_name=?, last_name = ?, age = ?, group_id = ? WHERE id=?";
    public static final String TRANSFER_STUDENT_TO_ANOTHER_GROUP = "UPDATE students SET group_id = ? WHERE id = ?";

    public static final String DELETE_STUDENT_FROM_ALL_COURSES = "DELETE FROM students_courses WHERE student_id=?";
    public static final String DELETE_STUDENT_FROM_STUDENTS_TABLE = "DELETE FROM students WHERE id=?";
    public static final String ASSIGN_STUDENT_TO_COURSE = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
    public static final String DELETE_STUDENT_FROM_COURSE = "DELETE FROM students_courses WHERE student_id=? AND course_id =?";
    public static final String UPDATE_STUDENT_ID = "UPDATE students_courses SET student_id=? WHERE student_id=?";
    public static final String READ_ONE_STUDENT_COURSE = "SELECT * FROM students_courses " +
                                                         "WHERE student_id = ? " +
                                                         "AND course_id = ?";
    public static final String READ_ALL_STUDENTS_COURSE_RELATION = "SELECT * FROM students_courses";

    public static final String DAO_EXCEPTION_MESSAGE = "There is no student with this ID in the database";
    public static final String DAO_EXCEPTION_MESSAGE_STUDENT_COURSE = "There is no group-course with this ID in the database";

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
        return jdbcTemplate.query(READ_STUDENTS_RELATED_TO_GROUP, new Object[]{groupId}, new StudentMapper(jdbcTemplate));
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
        jdbcTemplate.update(UPDATE,
                studentForQuery.getFirstName(),
                studentForQuery.getLastName(),
                studentForQuery.getAge(),
                studentForQuery.getGroup().getGroupId(),
                id);
    }

    public void deleteStudentsFromGroup(int groupId) {
        jdbcTemplate.update(DELETE_STUDENTS_FROM_GROUP, groupId);
    }

    @Override
    public void delete(int studentId) {
        deleteStudentFromAllCourses(studentId);
        jdbcTemplate.update(DELETE_STUDENT_FROM_STUDENTS_TABLE, studentId);
    }

    public void deleteStudentFromAllCourses(int studentId) {
        jdbcTemplate.update(DELETE_STUDENT_FROM_ALL_COURSES, studentId);
    }

    public void changeStudentGroup(int studentId, int groupId) {
        jdbcTemplate.update(TRANSFER_STUDENT_TO_ANOTHER_GROUP, groupId, studentId);
    }

    public void assignStudentToCourse(int studentId, int courseId) {
        jdbcTemplate.update(ASSIGN_STUDENT_TO_COURSE, studentId, courseId);
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        jdbcTemplate.update(DELETE_STUDENT_FROM_COURSE, studentId, courseId);
    }

    public void updateStudentId(int studentId, int updatedId) {
        jdbcTemplate.update(UPDATE_STUDENT_ID, updatedId, studentId);
    }

    public StudentCourse readOneStudentCourse(int studentId, int courseId) {
        StudentCourse studentCourse = jdbcTemplate.query(READ_ONE_STUDENT_COURSE,
                new Object[]{studentId, courseId}, new StudentCourseMapper())
                .stream()
                .findAny()
                .orElse(null);

        if (studentCourse == null) {
            try {
                throw new DAOException(DAO_EXCEPTION_MESSAGE_STUDENT_COURSE);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

        return studentCourse;
    }

    public List<StudentCourse> readAllStudentsCourseRelation() {
        return jdbcTemplate.query(READ_ALL_STUDENTS_COURSE_RELATION, new StudentCourseMapper());
    }
}
