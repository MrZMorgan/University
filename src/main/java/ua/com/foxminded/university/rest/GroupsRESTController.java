package ua.com.foxminded.university.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.services.interfaces.GroupsService;
import java.util.List;

@RestController
@Api(tags = "Groups API")
@RequestMapping("api/groups")
public class GroupsRESTController {

    private static final String DELETE_MESSAGE = "Group with id %d was deleted";
    private GroupsService groupsService;

    @Autowired
    public GroupsRESTController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @GetMapping()
    @ApiOperation(value = "Show information about all groups in database",
            response = Group.class)
    private List<Group> showAllGroups() {
        List<Group> groups = groupsService.readTable();
        return groups;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get info about one group from DB by course id",
            response = Group.class)
    public Group getGroup(@PathVariable int id){
        Group group = groupsService.readOneRecordFromTable(id);
        return group;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete group from database by id",
            response = Group.class)
    public String deleteGroup(@PathVariable int id) {
        groupsService.deleteGroupById(id);
        return String.format(DELETE_MESSAGE, id);
    }

    @PostMapping()
    @ApiOperation(value = "Create new group",
            response = Group.class)
    public Group createGroup(@RequestBody Group group) {
        groupsService.createGroup(group);
        return group;
    }

    @PutMapping()
    @ApiOperation(value = "Update group data",
            response = Group.class)
    public Group updateGroup(@RequestBody Group group) {
        groupsService.createGroup(group);
        return group;
    }
}
