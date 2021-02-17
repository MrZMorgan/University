package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.services.GroupsServiceImpl;
import ua.com.foxminded.university.services.interfaces.GroupsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupsController {

    private GroupsService groupsServiceImpl;

    @Autowired
    public GroupsController(GroupsServiceImpl groupsServiceImpl) {
        this.groupsServiceImpl = groupsServiceImpl;
    }

    @GetMapping()
    private ModelAndView showAllGroups(Model model) {
        List<Group> groups = groupsServiceImpl.readTable();
        model.addAttribute("allGroups", groups);
        return new ModelAndView("groups/all-groups", "allGroups", groups);
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteGroup(@PathVariable int id) {
        groupsServiceImpl.deleteGroupById(id);
        return new ModelAndView("redirect:/groups");
    }

    @GetMapping("/new")
    public ModelAndView newGroup(@ModelAttribute("group") @RequestBody Group group) {
        return new ModelAndView("groups/new", "group", group);
    }

    @PostMapping()
    public ModelAndView createGroups(@ModelAttribute("group") @RequestBody @Valid Group group,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("groups/new", "group", group);
        }
        groupsServiceImpl.createGroup(group);
        return new ModelAndView("redirect:/groups", "group", group);
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editGroup(Model model, @PathVariable("id") int id) {
        Group group = groupsServiceImpl.readOneRecordFromTable(id);
        model.addAttribute("group", group);
        return new ModelAndView("groups/edit", "group", group);
    }

    @PatchMapping("/{id}")
    public ModelAndView updateGroup(@ModelAttribute("group") @RequestBody @Valid Group group,
                                    BindingResult bindingResult,
                                    @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("groups/edit", "group", group);
        }
        groupsServiceImpl.updateGroupData(id, group);
        return new ModelAndView("redirect:/groups", "group", group);
    }
}
