package ua.com.foxminded.university.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.foxminded.university.dao.GroupsJdbcDao;
import ua.com.foxminded.university.dao.StudentsCoursesJdbcDao;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.models.Course;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.models.Student;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentsServiceTest {

    private EmbeddedDatabase embeddedDatabase;
    private StudentsCoursesJdbcDao studentsCoursesJdbcDao;
    private StudentsJdbcDao studentsJdbcDao;
    private StudentsService studentsService;
    private GroupsJdbcDao groupsJdbcDao;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        studentsCoursesJdbcDao = new StudentsCoursesJdbcDao(jdbcTemplate);
        studentsJdbcDao = new StudentsJdbcDao(jdbcTemplate);
        groupsJdbcDao = new GroupsJdbcDao(jdbcTemplate);
        studentsService = new StudentsService(studentsCoursesJdbcDao, studentsJdbcDao);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void shouldDeleteStudentById() {
        int studentIdToDelete = 1;

        studentsService.deleteStudentById(studentIdToDelete);

        int expectedStudentsTableSize = 3;
        int actualStudentsTableSize = studentsJdbcDao.read().size();

        int expectedStudentsCoursesTableSize = 3;
        int actualStudentsCoursesTableSize = studentsCoursesJdbcDao.read().size();

        assertEquals(expectedStudentsTableSize, actualStudentsTableSize);
        assertEquals(expectedStudentsCoursesTableSize, actualStudentsCoursesTableSize);
    }

    @Test
    void shouldTransferStudentToAnotherGroup() {
        int studentId = 1;
        int newGroupId = 2;

        studentsService.transferStudentToAnotherGroup(studentId, newGroupId);

        int actualGroupId = studentsJdbcDao.read(studentId).getGroup().getGroupId();

        assertEquals(newGroupId, actualGroupId);
    }

    @Test
    void shouldDeleteStudentFromGroup() {
        int studentId = 1;

        studentsService.deleteStudentFromGroup(studentId);

        Group actualGroup = studentsJdbcDao.read(studentId).getGroup();

        assertNull(actualGroup);
    }

    @Test
    void shouldAssignStudentToCourse () {
        int studentId = 1;
        int courseId = 2;

        studentsService.assignStudentToCourse(studentId, courseId);

        int actualCourseId = studentsCoursesJdbcDao.read(studentId, courseId).getCourseId();

        assertEquals(courseId, actualCourseId);
    }

    @Test
    void shouldDeleteStudentFromCourse() {
        int studentId = 1;
        int courseId = 1;

        studentsService.deleteStudentFromCourse(studentId, courseId);

        assertNull(studentsCoursesJdbcDao.read(studentId, courseId));
    }

    @Test
    void shouldDeleteStudentFromAllCourses() {
        int studentId = 1;
        int courseId = 2;

        studentsService.assignStudentToCourse(studentId, courseId);

        int expectedTableSize = 5;
        int actualTableSize = studentsCoursesJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);

        studentsService.deleteStudentsFromAllCourses(studentId);

        expectedTableSize = 3;
        actualTableSize = studentsCoursesJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void shouldCreateStudent() {
        Group groupForTest = groupsJdbcDao.read(1);
        List<Course> coursesForTest = new LinkedList<>();

        int studentId = 5;
        String firstNameForTest = "firstName";
        String lastNameForTest = "lastName";
        int ageForTest = 30;
        Student student = new Student(studentId, firstNameForTest, lastNameForTest, ageForTest, groupForTest, coursesForTest);

        studentsService.createStudent(student);

        Student actualStudent = studentsJdbcDao.read(studentId);

        assertEquals(student, actualStudent);
    }
}