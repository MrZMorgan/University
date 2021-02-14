package ua.com.foxminded.university.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.interfaces.GroupsRepository;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.services.interfaces.GroupsService;
import java.util.List;
import java.util.Optional;

@Service
public class GroupsServiceImpl implements GroupsService {

    private GroupsRepository groupsRepository;

    @Autowired
    public GroupsServiceImpl(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
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
    public void assignGroupToCourse(int groupId, int courseId) {

    }
}
