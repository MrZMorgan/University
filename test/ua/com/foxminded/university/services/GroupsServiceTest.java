package ua.com.foxminded.university.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.foxminded.university.Application;
import ua.com.foxminded.university.H2JpaConfig;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.services.interfaces.CoursesService;
import ua.com.foxminded.university.services.interfaces.GroupsService;
import ua.com.foxminded.university.services.interfaces.StudentsService;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {Application.class, H2JpaConfig.class})
@ActiveProfiles("test")
@Transactional
class GroupsServiceTest {

    private final static String GROUP_NAME_FOR_TEST = "testGroupName";

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private StudentsService studentsService;

    @Test
    void shouldDeleteGroupById() {
        groupsService.deleteGroupById(1);
        int expectedGroupSize = 1;
        int actualGroupSize = groupsService.readTable().size();
        assertEquals(expectedGroupSize, actualGroupSize);
    }

    @Test
    void shouldCreateNewGroup() {
        groupsService.createGroup(new Group(GROUP_NAME_FOR_TEST));
        int expectedGroupSize = 3;
        int actualGroupSize = groupsService.readTable().size();
        assertEquals(expectedGroupSize, actualGroupSize);
    }

    @Test
    void shouldRenameGroup() {
        int groupIdToRename = 1;
        groupsService.renameGroup(groupIdToRename, GROUP_NAME_FOR_TEST);
        String actualGroupName = groupsService.readOneRecordFromTable(groupIdToRename).getName();
        assertEquals(GROUP_NAME_FOR_TEST, actualGroupName);
    }

    @Test
    void shouldReadOneRecordFromTable() {
        Group actualGroup = groupsService.readOneRecordFromTable(1);
        Group expectedGroup = expectedFirstGroup();
        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldReadTable() {
        List<Group> actualGroups = groupsService.readTable();
        List<Group> expectedGroups = new ArrayList<>();

        Group group1 = expectedFirstGroup();
        Group group2 = expectedSecondGroup();

        expectedGroups.add(group1);
        expectedGroups.add(group2);

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void shouldUpdateGroupData() {
        int groupIdToUpdate = 1;
        Group groupToUpdate = new Group(GROUP_NAME_FOR_TEST);
        groupsService.updateGroupData(groupIdToUpdate, groupToUpdate);
        String updatedGroupName = groupsService.readOneRecordFromTable(1).getName();

        assertEquals(GROUP_NAME_FOR_TEST, updatedGroupName);
    }

    @Test
    void shouldAssignGroupToCourse() {
        int groupId = 1;
        int courseId = 2;
        groupsService.assignGroupToCourse(groupId, courseId);

        int actualCourseId = groupsService.readOneRecordFromTable(groupId).getCourses().get(1).getId();
        assertEquals(courseId, actualCourseId);
    }

    Group expectedFirstGroup() {
        int groupId = 1;
        String groupName = "GS-10-1";

        Group group = new Group();
        group.setId(groupId);
        group.setName(groupName);

        Student student1 = studentsService.readOneRecordFromTable(1);
        Student student2 = studentsService.readOneRecordFromTable(2);
        List<Student> studentsFromFirstGroup = new ArrayList<>();
        studentsFromFirstGroup.add(student1);
        studentsFromFirstGroup.add(student2);

        Course course1 = coursesService.readOneRecordFromTable(1);
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
        group.setId(groupId);
        group.setName(groupName);

        Student student1 = studentsService.readOneRecordFromTable(3);
        Student student2 = studentsService.readOneRecordFromTable(4);
        List<Student> studentsFromFirstGroup = new ArrayList<>();
        studentsFromFirstGroup.add(student1);
        studentsFromFirstGroup.add(student2);

        Course course1 = coursesService.readOneRecordFromTable(2);
        List<Course> coursesFromFirstGroup = new ArrayList<>();
        coursesFromFirstGroup.add(course1);

        group.setCourses(coursesFromFirstGroup);
        group.setStudents(studentsFromFirstGroup);

        return group;
    }
}