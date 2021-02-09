package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.foxminded.university.config.TestConfig;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class GroupsJdbcDaoTest {

    private final static String NAME_FOR_TEST = "GS-10-1";
    private final static String NAME_FOR_TEST_2 = "ERB-11-2";
    @Autowired
    private GroupsJdbcDao groupsJdbcDao;
    @Autowired
    private StudentsJdbcDao studentsJdbcDao;

    @Test
    @Transactional
    void shouldCreateGroup() {
        groupsJdbcDao.create(new Group(NAME_FOR_TEST));

        int expectedTableSize = 3;
        int actualTableSize = groupsJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    @Transactional
    void shouldReadGroup() {
        List<Student> students = new LinkedList<>();

        Group actualGroup = groupsJdbcDao.read(1);
        Group expectedGroup = new Group(NAME_FOR_TEST);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    @Transactional
    void shouldReadAllGroups() {
        List<Group> actualGroupsList = groupsJdbcDao.read();
        List<Group> expectedGroupsList = new LinkedList<>();

        List<Student> studentsFromGroup1 = new LinkedList<>();
        List<Student> studentsFromGroup2 = new LinkedList<>();

        expectedGroupsList.add(new Group(NAME_FOR_TEST));
        expectedGroupsList.add(new Group(NAME_FOR_TEST_2));

        assertEquals(expectedGroupsList, actualGroupsList);
    }

    @Test
    @Transactional
    void shouldUpdateGroup() {
        Group groupToUpdate = new Group(NAME_FOR_TEST);
        groupsJdbcDao.update(2, groupToUpdate);

        Group actualUpdatedGroup = groupsJdbcDao.read(1);

        List<Student> students = new LinkedList<>();
        Group expectedUpdatedGroup = new Group(NAME_FOR_TEST);

        assertEquals(expectedUpdatedGroup, actualUpdatedGroup);
    }

    @Test
    @Transactional
    void shouldDeleteGroup() {
        int groupIdToDelete = 2;

        studentsJdbcDao.deleteStudentsFromGroup(groupIdToDelete);
        groupsJdbcDao.delete(groupIdToDelete);

        int expectedTableSize = 1;
        int actualTableSize = groupsJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }
}