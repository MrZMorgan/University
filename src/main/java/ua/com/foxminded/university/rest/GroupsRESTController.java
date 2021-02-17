package ua.com.foxminded.university.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.services.interfaces.GroupsService;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupsRESTController {

    private static final String DELETE_MESSAGE = "Groups with id %d was deleted";
    private GroupsService groupsService;

    @Autowired
    public GroupsRESTController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @GetMapping()
    private List<Group> showAllGroups() {
        List<Group> groups = groupsService.readTable();
        return groups;
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable int id){
        Group group = groupsService.readOneRecordFromTable(id);
        return group;
    }

    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable int id) {
        groupsService.deleteGroupById(id);
        return String.format(DELETE_MESSAGE, id);
    }

    @PostMapping()
    public Group createGroup(@RequestBody Group group) {
        groupsService.createGroup(group);
        return group;
    }

    @PutMapping()
    public Group updateGroup(@RequestBody Group group) {
        groupsService.createGroup(group);
        return group;
    }
}
