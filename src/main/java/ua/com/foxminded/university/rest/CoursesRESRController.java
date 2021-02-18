package ua.com.foxminded.university.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.services.CoursesServiceImpl;
import ua.com.foxminded.university.services.interfaces.CoursesService;
import java.util.List;

@RestController
@RequestMapping("api/courses")
public class CoursesRESRController {

    private static final String DELETE_MESSAGE = "Course with id %d was deleted";
    private CoursesService coursesServiceImpl;

    @Autowired
    public CoursesRESRController(CoursesServiceImpl coursesServiceImpl) {
        this.coursesServiceImpl = coursesServiceImpl;
    }

    @GetMapping()
    private List<Course> showAllCourses() {
        List<Course> courses = coursesServiceImpl.readTable();
        return courses;
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable int id){
        Course course = coursesServiceImpl.readOneRecordFromTable(id);
        return course;
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable int id) {
        coursesServiceImpl.deleteCourseById(id);

        return String.format(DELETE_MESSAGE, id);
    }

    @PostMapping()
    public Course createCourse(@RequestBody Course course) {
        coursesServiceImpl.createCourse(course);
        return course;
    }

    @PutMapping()
    public Course updateGroup(@RequestBody Course course) {
        coursesServiceImpl.createCourse(course);
        return course;
    }
}
