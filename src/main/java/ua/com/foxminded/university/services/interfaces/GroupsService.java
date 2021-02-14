package ua.com.foxminded.university.services.interfaces;

import ua.com.foxminded.university.entities.Group;
import java.util.List;

public interface GroupsService {

    public void deleteGroupById(int groupId);

    public void createGroup(Group group);

    public void renameGroup(int groupIdToRename, String newGroupName);

    public Group readOneRecordFromTable(int groupId);

    public List<Group> readTable();

    public void updateGroupData(int id, Group groupForQuery);

    public void assignGroupToCourse(int groupId, int courseId);
}
