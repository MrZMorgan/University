package ua.com.foxminded.university.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.services.interfaces.TeacherService;

import java.util.List;

@RestController
@Api(tags = "Teacher API")
@RequestMapping("api/teachers")
public class TeachersRESTController {

    private static final String DELETE_MESSAGE = "Teacher with id %d was deleted";
    private TeacherService teacherService;

    @Autowired
    public TeachersRESTController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping()
    @ApiOperation(value = "Show information about all teachers in database",
            response = Teacher.class)
    private List<Teacher> showAllTeachers() {
        List<Teacher> teachers = teacherService.readTable();
        return teachers;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get info about teacher from DB by course id",
            response = Teacher.class)
    public Teacher getTeacher(@PathVariable int id){
        Teacher teacher = teacherService.readOneRecordFromTable(id);
        return teacher;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete teacher from database by id",
            response = Teacher.class)
    public String deleteTeacher(@PathVariable int id) {
        teacherService.deleteTeacherById(id);
        return String.format(DELETE_MESSAGE, id);
    }

    @PostMapping()
    @ApiOperation(value = "Create new teacher",
            response = Teacher.class)
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        teacherService.createTeacher(teacher);
        return teacher;
    }

    @PutMapping()
    @ApiOperation(value = "Update teacher data",
            response = Teacher.class)
    public Teacher updateTeacher(@RequestBody Teacher teacher) {
        teacherService.createTeacher(teacher);
        return teacher;
    }
}
