package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.foxminded.university.models.GroupCourse;

import static org.junit.jupiter.api.Assertions.*;

class GroupsCoursesJdbcDaoTest {

    private EmbeddedDatabase embeddedDatabase;
    private JdbcTemplate jdbcTemplate;
    private GroupsCoursesJdbcDao groupsCoursesJdbcDao;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        groupsCoursesJdbcDao = new GroupsCoursesJdbcDao(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void create() {
        groupsCoursesJdbcDao.create(1, 2);

        int actualTableSize = groupsCoursesJdbcDao.read().size();
        int expectedTableSize = 3;

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void read() {
        GroupCourse expectedGroupCourse = new GroupCourse(1, 1);
        GroupCourse actualGroupCourse = groupsCoursesJdbcDao.read(1, 1);

        assertEquals(expectedGroupCourse, actualGroupCourse);
    }

    @Test
    void updateCourseId() {
        groupsCoursesJdbcDao.updateCourseId(1, 1, 2);

        GroupCourse actualGroupCourse = groupsCoursesJdbcDao.read(2, 1);
        GroupCourse expectedGroupCourse = new GroupCourse(1, 2);

        assertEquals(expectedGroupCourse, actualGroupCourse);
    }

    @Test
    void deleteCourse() {
        groupsCoursesJdbcDao.deleteCourse(1);

        int actualTableSize = groupsCoursesJdbcDao.read().size();
        int expectedTableSize = 1;

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void deleteGroup() {
        groupsCoursesJdbcDao.deleteCourse(1);

        int actualTableSize = groupsCoursesJdbcDao.read().size();
        int expectedTableSize = 1;

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void deleteGroupFromCourse() {
        groupsCoursesJdbcDao.deleteGroupFromCourse(1, 1);

        int actualTableSize = groupsCoursesJdbcDao.read().size();
        int expectedTableSize = 1;

        assertEquals(expectedTableSize, actualTableSize);
    }
}