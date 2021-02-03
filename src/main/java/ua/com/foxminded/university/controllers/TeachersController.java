package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.university.models.Teacher;
import ua.com.foxminded.university.services.TeacherService;

import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeachersController {

    private TeacherService teacherService;

    @Autowired
    public TeachersController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping()
    private String showAllTeachers(Model model) {
        List<Teacher> teachers = teacherService.readTable();
        model.addAttribute("allTeachers", teachers);

        return "teachers/all-teachers";
    }

    @GetMapping("/{id}")
    public String deleteTeacher(@PathVariable int id) {
        teacherService.deleteTeacherById(id);
        return "redirect:/teachers";
    }
}
