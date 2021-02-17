package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.services.StudentsServiceImpl;
import ua.com.foxminded.university.services.interfaces.StudentsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {

    private StudentsService studentsServiceImpl;

    @Autowired
    public StudentsController(StudentsServiceImpl studentsServiceImpl) {
        this.studentsServiceImpl = studentsServiceImpl;
    }

    @GetMapping()
    private ModelAndView showAllStudents(Model model) {
        List<Student> students = studentsServiceImpl.readTable();
        model.addAttribute("allStudents", students);
        return new ModelAndView("students/all-students", "allStudents", students);
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteStudent(@PathVariable int id) {
        studentsServiceImpl.deleteStudentById(id);
        return new ModelAndView("redirect:/students");
    }

    @GetMapping("/new")
    public ModelAndView newStudent(@ModelAttribute("student") @RequestBody Student student) {
        return new ModelAndView("students/new", "student", student);
    }

    @PostMapping()
    public ModelAndView createStudent(@ModelAttribute("student") @RequestBody @Valid Student student,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("students/new", "student", student);
        }
        studentsServiceImpl.createStudent(student);
        return new ModelAndView("redirect:/students", "student", student);
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editStudent(Model model, @PathVariable("id") int id) {
        Student student = studentsServiceImpl.readOneRecordFromTable(id);
        model.addAttribute("student", student);
        return new ModelAndView("students/edit", "student", student);
    }

    @PatchMapping("/{id}")
    public ModelAndView updateStudent(@ModelAttribute("student") @RequestBody @Valid Student student,
                                BindingResult bindingResult,
                                @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("students/edit", "student", student);
        }
        studentsServiceImpl.updateStudentData(id, student);
        return new ModelAndView("redirect:/students", "student", student);
    }
}
