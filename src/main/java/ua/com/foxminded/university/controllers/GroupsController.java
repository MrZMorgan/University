package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.services.GroupsService;

import java.util.List;

@Controller

public class GroupsController {

    private GroupsService groupsService;

    @Autowired
    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @GetMapping("/groups")
    private String showAllGroups(Model model) {
        List<Group> groups = groupsService.readTable();
        model.addAttribute("allGroups", groups);

        return "groups/all-groups";
    }

}
