package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.foxminded.university.models.StudentCourse;
import static org.junit.jupiter.api.Assertions.*;

class StudentsCoursesJdbcDaoTest {

    private EmbeddedDatabase embeddedDatabase;
    private JdbcTemplate jdbcTemplate;
    private StudentsCoursesJdbcDao studentsCoursesJdbcDao;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        studentsCoursesJdbcDao = new StudentsCoursesJdbcDao(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void create() {
        studentsCoursesJdbcDao.create(1, 2);

        int actualTableSize = studentsCoursesJdbcDao.read().size();
        int expectedTableSize = 5;

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void read() {
        StudentCourse actualStudentCourse = studentsCoursesJdbcDao.read(1, 1);
        StudentCourse expectedStudentCourse = new StudentCourse(1, 1);

        assertEquals(expectedStudentCourse, actualStudentCourse);
    }

    @Test
    void updateCourseId() {
        studentsCoursesJdbcDao.updateCourseId(1, 2);

        StudentCourse actualStudentCourse = studentsCoursesJdbcDao.read(2, 2);
        StudentCourse expectedStudentCourse = new StudentCourse(2, 2);

        assertEquals(expectedStudentCourse, actualStudentCourse);
    }

    @Test
    void updateStudentId() {
        studentsCoursesJdbcDao.updateStudentId(3, 2);

        StudentCourse actualStudentCourse = studentsCoursesJdbcDao.read(2, 2);
        StudentCourse expectedStudentCourse = new StudentCourse(2, 2);

        assertEquals(expectedStudentCourse, actualStudentCourse);
    }

    @Test
    void deleteCourse() {
        studentsCoursesJdbcDao.deleteCourse(1);

        int actualTableSize = studentsCoursesJdbcDao.read().size();
        int expectedTableSize = 2;

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void deleteStudent() {
        studentsCoursesJdbcDao.deleteStudent(1);

        int actualTableSize = studentsCoursesJdbcDao.read().size();
        int expectedTableSize = 3;

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void deleteStudentFromCourse() {
        studentsCoursesJdbcDao.deleteStudentFromCourse(1, 1);

        int actualTableSize = studentsCoursesJdbcDao.read().size();
        int expectedTableSize = 3;

        assertEquals(expectedTableSize, actualTableSize);
    }
}