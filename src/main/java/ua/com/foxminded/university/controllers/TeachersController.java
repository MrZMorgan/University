package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.models.Student;
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

    @DeleteMapping("/{id}")
    public String deleteTeacher(@PathVariable int id) {
        teacherService.deleteTeacherById(id);
        return "redirect:/teachers";
    }

    @GetMapping("/new")
    public String newTeacher(@ModelAttribute("teacher") Teacher teacher) {
        return "teachers/new";
    }

    @PostMapping()
    public String createTeacher(@ModelAttribute("teacher") Teacher teacher) {
        teacherService.createTeacher(teacher);
        return "redirect:/teachers";
    }
}
