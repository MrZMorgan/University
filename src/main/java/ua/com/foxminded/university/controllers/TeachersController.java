package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.services.TeacherServiceImpl;
import ua.com.foxminded.university.services.interfaces.TeacherService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeachersController {

    private TeacherService teacherServiceImpl;

    @Autowired
    public TeachersController(TeacherServiceImpl teacherServiceImpl) {
        this.teacherServiceImpl = teacherServiceImpl;
    }

    @GetMapping()
    private ModelAndView showAllTeachers(Model model) {
        List<Teacher> teachers = teacherServiceImpl.readTable();
        model.addAttribute("allTeachers", teachers);
        return new ModelAndView("teachers/all-teachers", "allTeachers", teachers);
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteTeacher(@PathVariable int id) {
        teacherServiceImpl.deleteTeacherById(id);
        return new ModelAndView("redirect:/teachers");
    }

    @GetMapping("/new")
    public ModelAndView newTeacher(@ModelAttribute("teacher") @RequestBody Teacher teacher) {
        return new ModelAndView("teachers/new", "teacher", teacher);
    }

    @PostMapping()
    public ModelAndView createTeacher(@ModelAttribute("teacher") @RequestBody @Valid Teacher teacher,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("teachers/new", "teacher", teacher);
        }
        teacherServiceImpl.createTeacher(teacher);
        return new ModelAndView("redirect:/teachers", "teacher", teacher);
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editTeacher(Model model, @PathVariable("id") int id) {
        Teacher teacher = teacherServiceImpl.readOneRecordFromTable(id);
        model.addAttribute("teacher", teacher);
        return new ModelAndView("teachers/edit", "teacher", teacher);
    }

    @PatchMapping("/{id}")
    public ModelAndView updateTeacher(@ModelAttribute("teacher") @RequestBody @Valid Teacher teacher,
                                BindingResult bindingResult,
                                @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("teachers/edit", "teacher", teacher);
        }
        teacherServiceImpl.updateTeacherData(id, teacher);
        return new ModelAndView("redirect:/teachers", "teacher", teacher);
    }
}
