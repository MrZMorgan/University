package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.services.TeacherServiceImpl;
import ua.com.foxminded.university.services.interfaces.TeacherService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeachersController {

    private TeacherService teacherServiceImpl;

    @Autowired
    public TeachersController(TeacherServiceImpl teacherServiceImpl) {
        this.teacherServiceImpl = teacherServiceImpl;
    }

    @GetMapping()
    private String showAllTeachers(Model model) {
        List<Teacher> teachers = teacherServiceImpl.readTable();
        model.addAttribute("allTeachers", teachers);

        return "teachers/all-teachers";
    }

    @DeleteMapping("/{id}")
    public String deleteTeacher(@PathVariable int id) {
        teacherServiceImpl.deleteTeacherById(id);
        return "redirect:/teachers";
    }

    @GetMapping("/new")
    public String newTeacher(@ModelAttribute("teacher") Teacher teacher) {
        return "teachers/new";
    }

    @PostMapping()
    public String createTeacher(@ModelAttribute("teacher") @Valid Teacher teacher,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "teachers/new";
        }
        teacherServiceImpl.createTeacher(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/{id}/edit")
    public String editTeacher(Model model, @PathVariable("id") int id) {
        model.addAttribute("teacher", teacherServiceImpl.readOneRecordFromTable(id));
        return "teachers/edit";
    }

    @PatchMapping("/{id}")
    public String updateTeacher(@ModelAttribute("teacher") @Valid Teacher teacher,
                                BindingResult bindingResult,
                                @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "teachers/edit";
        }
        teacherServiceImpl.updateTeacherData(id, teacher);
        return "redirect:/teachers";
    }
}
