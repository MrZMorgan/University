package ua.com.foxminded.university.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.foxminded.university.dao.*;
import ua.com.foxminded.university.models.Student;
import static org.junit.jupiter.api.Assertions.*;

class GroupsServiceTest {

    private EmbeddedDatabase embeddedDatabase;
    private GroupsService groupsService;
    private GroupsCoursesJdbcDao groupsCoursesJdbcDao;
    private StudentsJdbcDao studentsJdbcDao;
    private GroupsJdbcDao groupsJdbcDao;


    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        groupsCoursesJdbcDao = new GroupsCoursesJdbcDao(jdbcTemplate);
        studentsJdbcDao = new StudentsJdbcDao(jdbcTemplate);
        groupsJdbcDao = new GroupsJdbcDao(jdbcTemplate);
        groupsService = new GroupsService(groupsCoursesJdbcDao, studentsJdbcDao, groupsJdbcDao);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void deleteGroupById() {
        int groupIdToDelete = 1;

        groupsService.deleteGroupById(groupIdToDelete);

        int expectedGroupsTableSize = 1;
        int actualGroupsTableSize = groupsJdbcDao.read().size();

        int expectedGroupsCoursesTableSize = 1;
        int actualGroupsCoursesTableSize = groupsCoursesJdbcDao.read().size();

        Student actualStudent1 = studentsJdbcDao.read(1);
        Student actualStudent2 = studentsJdbcDao.read(2);

        assertEquals(expectedGroupsTableSize, actualGroupsTableSize);
        assertEquals(expectedGroupsCoursesTableSize, actualGroupsCoursesTableSize);
        assertNull(actualStudent1.getGroup());
        assertNull(actualStudent2.getGroup());
    }
}