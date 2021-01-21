package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.models.Student;

import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GroupsJdbcDaoTest {

    private final static String NAME_FOR_TEST = "groupNameForTest";
    private EmbeddedDatabase embeddedDatabase;
    private GroupsJdbcDao groupsJdbcDao;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        groupsJdbcDao = new GroupsJdbcDao(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void shouldCreateGroup() {
        groupsJdbcDao.create(new Group(3, NAME_FOR_TEST, new LinkedList<>()));

        int expectedTableSize = 3;
        int actualTableSize = groupsJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void shouldReadGroup() {
        List<Student> students = new LinkedList<>();

        Group actualGroup = groupsJdbcDao.read(1);
        Group expectedGroup = new Group(1, NAME_FOR_TEST, students);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldReadAllGroups() {
        List<Group> actualGroupsList = groupsJdbcDao.read();
        List<Group> expectedGroupsList = new LinkedList<>();

        List<Student> studentsFromGroup1 = new LinkedList<>();
        List<Student> studentsFromGroup2 = new LinkedList<>();

        expectedGroupsList.add(new Group(1, NAME_FOR_TEST, studentsFromGroup1));
        expectedGroupsList.add(new Group(2, NAME_FOR_TEST, studentsFromGroup2));

        assertEquals(expectedGroupsList, actualGroupsList);
    }

    @Test
    void shouldUpdateGroup() {
        Group groupToUpdate = new Group(1, NAME_FOR_TEST, new LinkedList<>());
        groupsJdbcDao.update(2, groupToUpdate);

        Group actualUpdatedGroup = groupsJdbcDao.read(1);

        List<Student> students = new LinkedList<>();
        Group expectedUpdatedGroup = new Group(1, NAME_FOR_TEST, students);

        assertEquals(expectedUpdatedGroup, actualUpdatedGroup);
    }

    @Test
    void shouldDeleteGroup() {
        int groupIdToDelete = 2;

        groupsJdbcDao.delete(groupIdToDelete);

        int expectedTableSize = 1;
        int actualTableSize = groupsJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }
}