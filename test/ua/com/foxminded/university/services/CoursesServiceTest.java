package ua.com.foxminded.university.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.foxminded.university.dao.*;
import static org.junit.jupiter.api.Assertions.*;

class CoursesServiceTest {

    private EmbeddedDatabase embeddedDatabase;
    private StudentsCoursesJdbcDao studentsCoursesJdbcDao;
    private GroupsCoursesJdbcDao groupsCoursesJdbcDao;
    private CoursesJdbcDao coursesJdbcDao;
    private CoursesService coursesService;

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
        coursesService = new CoursesService(studentsCoursesJdbcDao, groupsCoursesJdbcDao, coursesJdbcDao);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void deleteCourseById() {
        int courseIdToDelete = 1;
        coursesService.deleteCourseById(courseIdToDelete);

        int expectedCourseTableSize = 1;
        int actualCourseTableSize = coursesJdbcDao.read().size();

        int expectedGroupCoursesTableSize = 1;
        int actualGroupCoursesTableSize = groupsCoursesJdbcDao.read().size();

        int expectedStudentsCoursesTableSize = 2;
        int actualStudentsCoursesTableSize = studentsCoursesJdbcDao.read().size();

        assertEquals(expectedCourseTableSize, actualCourseTableSize);
        assertEquals(expectedGroupCoursesTableSize, actualGroupCoursesTableSize);
        assertEquals(expectedStudentsCoursesTableSize, actualStudentsCoursesTableSize);
    }
}