package ua.com.foxminded.university.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.foxminded.university.dao.StudentsCoursesJdbcDao;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import static org.junit.jupiter.api.Assertions.*;

class StudentsServiceTest {

    private EmbeddedDatabase embeddedDatabase;
    private StudentsCoursesJdbcDao studentsCoursesJdbcDao;
    private StudentsJdbcDao studentsJdbcDao;
    private StudentsService studentsService;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        studentsCoursesJdbcDao = new StudentsCoursesJdbcDao(jdbcTemplate);
        studentsJdbcDao = new StudentsJdbcDao(jdbcTemplate);
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
}