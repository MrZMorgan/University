package ua.com.foxminded.university.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.dao.*;
import ua.com.foxminded.university.entities.Group;
import static org.mockito.Mockito.*;

class GroupsServiceTest {

    private StudentsJdbcDao studentsJdbcDaoMock;
    private GroupsJdbcDao groupsJdbcDaoMock;
    private GroupsService groupsService;


    @BeforeEach
    void setUp() {
        studentsJdbcDaoMock = mock(StudentsJdbcDao.class);
        groupsJdbcDaoMock = mock(GroupsJdbcDao.class);
        groupsService = new GroupsService(studentsJdbcDaoMock, groupsJdbcDaoMock);
    }

    @Test
    void shouldDeleteGroupById() {
        int groupId = anyInt();
        groupsService.deleteGroupById(groupId);
        verify(groupsJdbcDaoMock, times(1)).delete(anyInt());
    }

    @Test
    void shouldCreateNewGroup() {
        Group groupMock = mock(Group.class);
        groupsService.createGroup(groupMock);
        verify(groupsJdbcDaoMock, times(1)).create(groupMock);
    }

    @Test
    void shouldRenameGroup() {
        int groupIdToRename = anyInt();
        String newGroupName = anyString();
        groupsService.renameGroup(groupIdToRename, newGroupName);
        verify(groupsJdbcDaoMock, times(1)).renameGroup(groupIdToRename, newGroupName);
    }

    @Test
    void shouldReadOneRecordFromTable() {
        int groupId = anyInt();
        groupsService.readOneRecordFromTable(groupId);
        verify(groupsJdbcDaoMock, times(1)).read(groupId);
    }

    @Test
    void shouldReadTable() {
        groupsService.readTable();
        verify(groupsJdbcDaoMock, times(1)).read();
    }

    @Test
    void shouldUpdateGroupData() {
        int groupId = 1;
        Group groupMock = mock(Group.class);
        groupsService.updateGroupData(groupId, groupMock);
        verify(groupsJdbcDaoMock, times(1)).update(groupId, groupMock);
    }

    @Test
    void shouldAssignGroupToCourse() {
        int groupId = anyInt();
        int courseId = anyInt();
        groupsService.assignGroupToCourse(groupId, courseId);
        verify(groupsJdbcDaoMock, times(1)).assignGroupToCourse(groupId, courseId);
    }
}