package ua.com.foxminded.university.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.services.interfaces.StudentsService;
import java.util.List;

@RestController
@RequestMapping("api/students")
public class StudentsRESTController {

    private static final String DELETE_MESSAGE = "Student with id %d was deleted";
    private StudentsService studentsService;

    @Autowired
    public StudentsRESTController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping()
    private List<Student> showAllStudents() {
        List<Student> students = studentsService.readTable();
        return students;
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id){
        Student student = studentsService.readOneRecordFromTable(id);
        return student;
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentsService.deleteStudentById(id);
        return String.format(DELETE_MESSAGE, id);
    }

    @PostMapping()
    public Student createStudent(@RequestBody Student student) {
        studentsService.createStudent(student);
        return student;
    }

    @PutMapping()
    public Student updateStudent(@RequestBody Student student) {
        studentsService.createStudent(student);
        return student;
    }
}
