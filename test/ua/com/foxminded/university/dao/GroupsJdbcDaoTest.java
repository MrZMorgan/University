package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.foxminded.university.config.TestConfig;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
    @Autowired
    private CoursesJdbcDao coursesJdbcDao;

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
        Group actualGroup = groupsJdbcDao.read(1);
        Group expectedGroup = expectedFirstGroup();

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    @Transactional
    void shouldReadAllGroups() {
        List<Group> actualGroupsList = groupsJdbcDao.read();
        List<Group> expectedGroupsList = new LinkedList<>();

        expectedGroupsList.add(expectedFirstGroup());
        expectedGroupsList.add(expectedSecondGroup());

        assertEquals(expectedGroupsList, actualGroupsList);
    }

    @Test
    @Transactional
    void shouldUpdateGroup() {
        Group groupToUpdate = new Group(NAME_FOR_TEST);
        groupsJdbcDao.update(2, groupToUpdate);

        Group actualUpdatedGroup = groupsJdbcDao.read(1);
        Group expectedUpdatedGroup = expectedFirstGroup();

        assertEquals(expectedUpdatedGroup, actualUpdatedGroup);
    }

    @Test
    @Transactional
    void shouldDeleteGroup() {
        int groupIdToDelete = 1;

        groupsJdbcDao.delete(groupIdToDelete);

        int expectedTableSize = 1;
        int actualTableSize = groupsJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    Group expectedFirstGroup() {
        int groupId = 1;
        String groupName = "GS-10-1";

        Group group = new Group();
        group.setId(1);
        group.setName(groupName);

        Student student1 = studentsJdbcDao.read(1);
        Student student2 = studentsJdbcDao.read(2);
        List<Student> studentsFromFirstGroup = new ArrayList<>();
        studentsFromFirstGroup.add(student1);
        studentsFromFirstGroup.add(student2);

        Course course1 = coursesJdbcDao.read(1);
        List<Course> coursesFromFirstGroup = new ArrayList<>();
        coursesFromFirstGroup.add(course1);

        group.setCourses(coursesFromFirstGroup);
        group.setStudents(studentsFromFirstGroup);

        return group;
    }

    Group expectedSecondGroup() {
        int groupId = 2;
        String groupName = "ERB-11-2";

        Group group = new Group();
        group.setId(2);
        group.setName(groupName);

        Student student1 = studentsJdbcDao.read(3);
        Student student2 = studentsJdbcDao.read(4);
        List<Student> studentsFromFirstGroup = new ArrayList<>();
        studentsFromFirstGroup.add(student1);
        studentsFromFirstGroup.add(student2);

        Course course1 = coursesJdbcDao.read(2);
        List<Course> coursesFromFirstGroup = new ArrayList<>();
        coursesFromFirstGroup.add(course1);

        group.setCourses(coursesFromFirstGroup);
        group.setStudents(studentsFromFirstGroup);

        return group;
    }
}