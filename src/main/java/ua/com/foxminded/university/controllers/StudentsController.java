package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Student;
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

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentsService.deleteStudentById(id);
        return "redirect:/students";
    }

    @GetMapping("/new")
    public String newStudent(@ModelAttribute("student") Student student) {
        return "students/new";
    }

    @PostMapping()
    public String createStudent(@ModelAttribute("student") Student student) {
        studentsService.createStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String editStudent(Model model, @PathVariable("id") int id) {
        model.addAttribute("student", studentsService.readOneRecordFromTable(id));
        return "students/edit";
    }

    @PatchMapping("/{id}")
    public String updateStudent(@ModelAttribute("student") Student student,
                                @PathVariable("id") int id) {
        studentsService.updateStudentData(id, student);
        return "redirect:/students";
    }
}
