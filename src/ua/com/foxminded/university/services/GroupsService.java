package ua.com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.GroupsCoursesJdbcDao;
import ua.com.foxminded.university.dao.GroupsJdbcDao;
import ua.com.foxminded.university.dao.StudentsJdbcDao;

@Service
public class GroupsService {

    private GroupsCoursesJdbcDao groupsCoursesJdbcDao;
    private StudentsJdbcDao studentsJdbcDao;
    private GroupsJdbcDao groupsJdbcDao;

    @Autowired
    public GroupsService(GroupsCoursesJdbcDao groupsCoursesJdbcDao,
                         StudentsJdbcDao studentsJdbcDao,
                         GroupsJdbcDao groupsJdbcDao) {
        this.groupsCoursesJdbcDao = groupsCoursesJdbcDao;
        this.studentsJdbcDao = studentsJdbcDao;
        this.groupsJdbcDao = groupsJdbcDao;
    }

    public void deleteGroupById(int groupId) {
        groupsCoursesJdbcDao.deleteGroup(groupId);
        studentsJdbcDao.deleteStudentFromGroup(groupId);
        groupsJdbcDao.delete(groupId);
    }


}
