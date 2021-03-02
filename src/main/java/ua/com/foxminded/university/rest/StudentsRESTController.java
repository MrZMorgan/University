package ua.com.foxminded.university.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.services.interfaces.StudentsService;
import java.util.List;

@RestController
@Api(tags = "Students API")
@RequestMapping("api/students")
public class StudentsRESTController {

    private static final String DELETE_MESSAGE = "Student with id %d was deleted";
    private StudentsService studentsService;

    @Autowired
    public StudentsRESTController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping()
    @ApiOperation(value = "Show information about all students in database",
            response = Student.class)
    private List<Student> showAllStudents() {
        List<Student> students = studentsService.readTable();
        return students;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get info about student from DB by course id",
            response = Student.class)
    public Student getStudent(@PathVariable int id){
        Student student = studentsService.readOneRecordFromTable(id);
        return student;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete student from database by id",
            response = Student.class)
    public String deleteStudent(@PathVariable int id) {
        studentsService.deleteStudentById(id);
        return String.format(DELETE_MESSAGE, id);
    }

    @PostMapping()
    @ApiOperation(value = "Create new student",
            response = Student.class)
    public Student createStudent(@RequestBody Student student) {
        studentsService.createStudent(student);
        return student;
    }

    @PutMapping()
    @ApiOperation(value = "Update student data",
            response = Student.class)
    public Student updateStudent(@RequestBody Student student) {
        studentsService.createStudent(student);
        return student;
    }
}
