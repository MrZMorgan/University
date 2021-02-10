package ua.com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.GroupsJdbcDao;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.entities.Group;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GroupsService {

    private StudentsJdbcDao studentsJdbcDao;
    private GroupsJdbcDao groupsJdbcDao;

    @Autowired
    public GroupsService(StudentsJdbcDao studentsJdbcDao,
                         GroupsJdbcDao groupsJdbcDao) {
        this.studentsJdbcDao = studentsJdbcDao;
        this.groupsJdbcDao = groupsJdbcDao;
    }

    @Transactional
    public void deleteGroupById(int groupId) {
        groupsJdbcDao.delete(groupId);
    }

    @Transactional
    public void createGroup(Group group) {
        groupsJdbcDao.create(group);
    }

    @Transactional
    public void renameGroup(int groupIdToRename, String newGroupName) {
        groupsJdbcDao.renameGroup(groupIdToRename, newGroupName);
    }

    @Transactional
    public Group readOneRecordFromTable(int groupId) {
        return groupsJdbcDao.read(groupId);
    }

    @Transactional
    public List<Group> readTable() {
        return groupsJdbcDao.read();
    }

    @Transactional
    public void updateGroupData(int id, Group groupForQuery) {
        groupsJdbcDao.update(id, groupForQuery);
    }

    @Transactional
    public void assignGroupToCourse(int groupId, int courseId) {
        groupsJdbcDao.assignGroupToCourse(groupId, courseId);
    }
}
