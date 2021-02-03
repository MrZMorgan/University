package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.university.models.Student;
import ua.com.foxminded.university.models.Teacher;
import ua.com.foxminded.university.services.StudentsService;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentsController {

    private StudentsService studentsService;

    @Autowired
    public StudentsController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping()
    private String showAllStudents(Model model) {
        List<Student> students = studentsService.readTable();
        model.addAttribute("allStudents", students);

        return "students/all-students";
    }

    @GetMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentsService.deleteStudentById(id);
        return "redirect:/students";
    }
}
