package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.services.GroupsService;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupsController {

    private GroupsService groupsService;

    @Autowired
    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @GetMapping()
    private String showAllGroups(Model model) {
        List<Group> groups = groupsService.readTable();
        model.addAttribute("allGroups", groups);

        return "groups/all-groups";
    }

    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable int id) {
        groupsService.deleteGroupById(id);
        return "redirect:/groups";
    }
}
