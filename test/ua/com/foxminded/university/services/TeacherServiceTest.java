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
    void deleteTeacherById() {
        int teacherIdToDelete = 1;

        teacherService.deleteTeacherById(teacherIdToDelete);

        int expectedTeachersTableSize = 1;
        int actualTeachersTableSize = teachersJdbcDao.read().size();

        Course actualCourse = coursesJdbcDao.read(1);

        assertEquals(expectedTeachersTableSize, actualTeachersTableSize);
        assertNull(actualCourse.getTeacher());
    }
}