package ua.com.foxminded.university.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.services.interfaces.TeacherService;

import java.util.List;

@RestController
@RequestMapping("api/teachers")
public class TeachersRESTController {


    private static final String DELETE_MESSAGE = "Teacher with id %d was deleted";
    private TeacherService teacherService;

    @Autowired
    public TeachersRESTController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping()
    private List<Teacher> showAllTeachers() {
        List<Teacher> teachers = teacherService.readTable();
        return teachers;
    }

    @GetMapping("/{id}")
    public Teacher getTeacher(@PathVariable int id){
        Teacher teacher = teacherService.readOneRecordFromTable(id);
        return teacher;
    }

    @DeleteMapping("/{id}")
    public String deleteTeacher(@PathVariable int id) {
        teacherService.deleteTeacherById(id);
        return String.format(DELETE_MESSAGE, id);
    }

    @PostMapping()
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        teacherService.createTeacher(teacher);
        return teacher;
    }

    @PutMapping()
    public Teacher updateTeacher(@RequestBody Teacher teacher) {
        teacherService.createTeacher(teacher);
        return teacher;
    }
}
