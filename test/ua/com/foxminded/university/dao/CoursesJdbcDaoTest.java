package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.foxminded.university.models.Course;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.models.Student;
import ua.com.foxminded.university.models.Teacher;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CoursesJdbcDaoTest {

    private final static String COURSE_NAME_FOR_TEST = "chemistry";
    private EmbeddedDatabase embeddedDatabase;
    private CoursesJdbcDao coursesJdbcDao;
    private TeachersJdbcDao teachersJdbcDao;
    private StudentsCoursesJdbcDao studentsCoursesJdbcDao;
    private GroupsCoursesJdbcDao groupsCoursesJdbcDao;
    private List<Group> groupsForTest = new LinkedList<>();
    private List<Student> studentsForTest = new LinkedList<>();

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        coursesJdbcDao = new CoursesJdbcDao(jdbcTemplate);
        teachersJdbcDao = new TeachersJdbcDao(jdbcTemplate);
        studentsCoursesJdbcDao = new StudentsCoursesJdbcDao(jdbcTemplate);
        groupsCoursesJdbcDao = new GroupsCoursesJdbcDao(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void shouldCreateCourse() {
        Teacher teacher = teachersJdbcDao.read(1);
        coursesJdbcDao.create(new Course(3, COURSE_NAME_FOR_TEST, teacher, groupsForTest, studentsForTest));

        int expectedTableSize = 3;
        int actualTableSize = coursesJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void shouldReadCourse() {
        Teacher teacherForTest = teachersJdbcDao.read(1);

        Course actualCourse = coursesJdbcDao.read(1);
        Course expectedCourse = new Course(1, "math", teacherForTest, groupsForTest, studentsForTest);

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void shouldReadAllCourses() {
        List<Course> actualCoursesList = coursesJdbcDao.read();
        List<Course> expectedCoursesList = new LinkedList<>();

        String firstCourseName = "math";
        String secondCourseName = "economy";

        Teacher teacherForMath = teachersJdbcDao.read(1);
        Teacher teacherForEconomy = teachersJdbcDao.read(2);

        expectedCoursesList.add(new Course(1, firstCourseName, teacherForMath, groupsForTest, studentsForTest));
        expectedCoursesList.add(new Course(2, secondCourseName, teacherForEconomy, groupsForTest, studentsForTest));

        assertEquals(expectedCoursesList, actualCoursesList);
    }

    @Test
    void shouldUpdateCourse() {
        Teacher teacher = teachersJdbcDao.read(1);
        Course courseToUpdate = new Course(1, COURSE_NAME_FOR_TEST, teacher, groupsForTest, studentsForTest);
        coursesJdbcDao.update(2, courseToUpdate);

        Course actualUpdatedCourse = coursesJdbcDao.read(2);
        Course expectedUpdatedCourse = new Course(2, COURSE_NAME_FOR_TEST, teacher, groupsForTest, studentsForTest);

        assertEquals(expectedUpdatedCourse, actualUpdatedCourse);
    }

    @Test
    void shouldDeleteCourse() {
        int courseIdToDelete = 2;

        studentsCoursesJdbcDao.deleteCourse(courseIdToDelete);
        groupsCoursesJdbcDao.deleteCourse(courseIdToDelete);

        coursesJdbcDao.delete(courseIdToDelete);

        int expectedTableSize = 1;
        int actualTableSize = coursesJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }
}