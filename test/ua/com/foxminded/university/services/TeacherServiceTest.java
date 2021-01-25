package ua.com.foxminded.university.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.dao.TeachersJdbcDao;
import ua.com.foxminded.university.models.Course;
import ua.com.foxminded.university.models.Teacher;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherServiceTest {

    private EmbeddedDatabase embeddedDatabase;
    private TeachersJdbcDao teachersJdbcDao;
    private CoursesJdbcDao coursesJdbcDao;
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        teachersJdbcDao = new TeachersJdbcDao(jdbcTemplate);
        coursesJdbcDao = new CoursesJdbcDao(jdbcTemplate);
        teacherService = new TeacherService(coursesJdbcDao, teachersJdbcDao);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void shouldDeleteTeacherById() {
        int teacherIdToDelete = 1;

        teacherService.deleteTeacherById(teacherIdToDelete);

        int expectedTeachersTableSize = 1;
        int actualTeachersTableSize = teachersJdbcDao.read().size();

        Course actualCourse = coursesJdbcDao.read(1);

        assertEquals(expectedTeachersTableSize, actualTeachersTableSize);
        assertNull(actualCourse.getTeacher());
    }

    @Test
    void shouldDeleteTeacherFromCourse() {
        int teacherId = 2;
        int courseIdToDelete = 2;

        teacherService.deleteTeacherFromCourse(teacherId, courseIdToDelete);

        assertNull(coursesJdbcDao.read(courseIdToDelete).getTeacher());
    }

    @Test
    void shouldAssignTeacherToCourse() {
        int teacherId = 2;
        int courseId = 1;
        teacherService.assignTeacherToCourse(teacherId, courseId);

        int actualTeacherId = coursesJdbcDao.read(courseId).getTeacher().getId();

        assertEquals(teacherId, actualTeacherId);
    }

    @Test
    void shouldCreateTeacher() {
        int id = 3;
        String firstNameForTest = "firstName";
        String lastNameForTest = "lastName";
        int ageForTest = 30;
        List<Course> coursesListForTest = new LinkedList<>();
        Teacher teacher = new Teacher(id, firstNameForTest, lastNameForTest, ageForTest, coursesListForTest);

        teacherService.createTeacher(teacher);

        Teacher actualTeacher = teachersJdbcDao.read(3);

        assertEquals(teacher, actualTeacher);
    }

    @Test
    void shouldDeleteTeacherFromAllCourses() {
        int teacherId = 1;
        int courseId = 2;

        coursesJdbcDao.assignTeacherToCourse(teacherId, courseId);
        teacherService.deleteTeacherFromAllCourses(teacherId);

        int actualCoursesRelatedToTeacher = coursesJdbcDao.readCoursesRelatedToTeacher(teacherId).size();
        int expectedCoursesRelatedToTeacher = 0;

        assertEquals(expectedCoursesRelatedToTeacher, actualCoursesRelatedToTeacher);
    }
}