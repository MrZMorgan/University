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
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

class GroupsServiceTest {

    private EmbeddedDatabase embeddedDatabase;
    private GroupsService groupsService;
    private StudentsJdbcDao studentsJdbcDao;
    private GroupsJdbcDao groupsJdbcDao;


    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        studentsJdbcDao = new StudentsJdbcDao(jdbcTemplate);
        groupsJdbcDao = new GroupsJdbcDao(jdbcTemplate);
        groupsService = new GroupsService(studentsJdbcDao, groupsJdbcDao);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void shouldDeleteGroupById() {

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