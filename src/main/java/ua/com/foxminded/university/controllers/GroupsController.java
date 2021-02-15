package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.services.GroupsServiceImpl;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupsController {

    private GroupsServiceImpl groupsServiceImpl;

    @Autowired
    public GroupsController(GroupsServiceImpl groupsServiceImpl) {
        this.groupsServiceImpl = groupsServiceImpl;
    }

    @GetMapping()
    private String showAllGroups(Model model) {
        List<Group> groups = groupsServiceImpl.readTable();
        model.addAttribute("allGroups", groups);

        return "groups/all-groups";
    }

    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable int id) {
        groupsServiceImpl.deleteGroupById(id);
        return "redirect:/groups";
    }

    @GetMapping("/new")
    public String newGroup(@ModelAttribute("group") Group group) {
        return "groups/new";
    }

    @PostMapping()
    public String createGroups(@ModelAttribute("group") Group group) {
        groupsServiceImpl.createGroup(group);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/edit")
    public String editGroup(Model model, @PathVariable("id") int id) {
        model.addAttribute("group", groupsServiceImpl.readOneRecordFromTable(id));
        return "groups/edit";
    }

    @PatchMapping("/{id}")
    public String updateGroup(@ModelAttribute("group") Group group,
                         @PathVariable("id") int id) {
        groupsServiceImpl.updateGroupData(id, group);
        return "redirect:/groups";
    }
}
