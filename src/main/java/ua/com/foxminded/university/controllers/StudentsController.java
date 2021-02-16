package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.services.StudentsServiceImpl;
import ua.com.foxminded.university.services.interfaces.StudentsService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentsController {

    private StudentsService studentsServiceImpl;

    @Autowired
    public StudentsController(StudentsServiceImpl studentsServiceImpl) {
        this.studentsServiceImpl = studentsServiceImpl;
    }

    @GetMapping()
    private String showAllStudents(Model model) {
        List<Student> students = studentsServiceImpl.readTable();
        model.addAttribute("allStudents", students);

        return "students/all-students";
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentsServiceImpl.deleteStudentById(id);
        return "redirect:/students";
    }

    @GetMapping("/new")
    public String newStudent(@ModelAttribute("student") Student student) {
        return "students/new";
    }

    @PostMapping()
    public String createStudent(@ModelAttribute("student") @Valid Student student,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "students/new";
        }
        studentsServiceImpl.createStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String editStudent(Model model, @PathVariable("id") int id) {
        model.addAttribute("student", studentsServiceImpl.readOneRecordFromTable(id));
        return "students/edit";
    }

    @PatchMapping("/{id}")
    public String updateStudent(@ModelAttribute("student") @Valid Student student,
                                BindingResult bindingResult,
                                @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "students/edit";
        }
        studentsServiceImpl.updateStudentData(id, student);
        return "redirect:/students";
    }
}
