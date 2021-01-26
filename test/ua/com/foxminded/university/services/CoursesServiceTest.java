package ua.com.foxminded.university.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.foxminded.university.dao.*;
import ua.com.foxminded.university.models.Course;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.models.Student;
import ua.com.foxminded.university.models.Teacher;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoursesServiceTest {

    private EmbeddedDatabase embeddedDatabase;
    private StudentsCoursesJdbcDao studentsCoursesJdbcDao;
    private GroupsCoursesJdbcDao groupsCoursesJdbcDao;
    private CoursesJdbcDao coursesJdbcDao;
    private CoursesService coursesService;
    private TeachersJdbcDao teachersJdbcDao;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        studentsCoursesJdbcDao = new StudentsCoursesJdbcDao(jdbcTemplate);
        groupsCoursesJdbcDao = new GroupsCoursesJdbcDao(jdbcTemplate);
        coursesJdbcDao = new CoursesJdbcDao(jdbcTemplate);
        teachersJdbcDao = new TeachersJdbcDao(jdbcTemplate);
        coursesService = new CoursesService(coursesJdbcDao);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void shouldDeleteCourseById() {

    }

    @Test
    void shouldCreateCourse() {
        Teacher teacher = teachersJdbcDao.read(1);
        String courseName = "chemistry";
        List<Group> groupsForTest = new LinkedList<>();
        List<Student> studentsForTest = new LinkedList<>();
        Course newCourse = new Course(3, courseName, teacher, groupsForTest, studentsForTest);

        coursesService.createCourse(newCourse);

        int expectedTableSize = 3;
        int actualTableSize = coursesJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void shouldRenameCourse() {
        int courseIdToRename = 1;
        String courseName = "chemistry";

        coursesService.renameCourse(courseIdToRename, courseName);

        String actualCourseName = coursesJdbcDao.read(courseIdToRename).getCourseName();

        assertEquals(courseName, actualCourseName);
    }
}