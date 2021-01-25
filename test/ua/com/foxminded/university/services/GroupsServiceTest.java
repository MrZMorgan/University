package ua.com.foxminded.university.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.foxminded.university.dao.*;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.models.Student;

import java.util.LinkedList;

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
    void shouldDeleteGroupById() {
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

    @Test
    void shouldCreateNewGroup() {
        String newGroupName = "new group";
        Group newGroup = new Group(3, newGroupName, new LinkedList<>());
        groupsService.createGroup(newGroup);

        int expectedTableSize = 3;
        int actualTableSize = groupsJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void shouldRenameGroup() {
        int groupIdToRename = 1;
        String newGroupName = "new group";

        groupsService.renameGroup(groupIdToRename, newGroupName);

        String actualGroupName = groupsJdbcDao.read(1).getGroupName();

        assertEquals(newGroupName, actualGroupName);
    }
}