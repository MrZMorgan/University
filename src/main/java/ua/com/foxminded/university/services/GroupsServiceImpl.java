package ua.com.foxminded.university.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CoursesRepository;
import ua.com.foxminded.university.dao.GroupsRepository;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.services.interfaces.GroupsService;
import java.util.List;
import java.util.Optional;

@Service
public class GroupsServiceImpl implements GroupsService {

    private GroupsRepository groupsRepository;
    private CoursesRepository coursesRepository;

    @Autowired
    public GroupsServiceImpl(GroupsRepository groupsRepository,
                             CoursesRepository coursesRepository) {
        this.groupsRepository = groupsRepository;
        this.coursesRepository = coursesRepository;
    }

    @Override
    public void deleteGroupById(int groupId) {
        groupsRepository.deleteById(groupId);
    }

    @Override
    public void createGroup(Group group) {
        groupsRepository.save(group);
    }

    @Override
    public void renameGroup(int groupIdToRename, String newGroupName) {
        Group group = readOneRecordFromTable(groupIdToRename);
        group.setName(newGroupName);
        groupsRepository.save(group);
    }

    @Override
    @SneakyThrows
    public Group readOneRecordFromTable(int groupId) {
        Group group;
        Optional<Group> optional = groupsRepository.findById(groupId);
        if (optional.isPresent()) {
            group = optional.get();
        } else {
            throw new DAOException(groupId);
        }
        return group;
    }

    @Override
    public List<Group> readTable() {
        return groupsRepository.findAll();
    }

    @Override
    public void updateGroupData(int id, Group groupForQuery) {
        Group group = readOneRecordFromTable(id);
        group.setName(groupForQuery.getName());
        group.setCourses(group.getCourses());
        group.setStudents(group.getStudents());
        groupsRepository.save(group);
    }

    @Override
    @SneakyThrows
    public void assignGroupToCourse(int groupId, int courseId) {
        Group group = readOneRecordFromTable(groupId);
        Course course;
        Optional<Course> optional = coursesRepository.findById(courseId);
        if (optional.isPresent()) {
            course = optional.get();
        } else {
            throw new DAOException(courseId);
        }
        List<Course> courses = group.getCourses();
        courses.add(course);
        groupsRepository.save(group);
    }
}
