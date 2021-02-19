package ua.com.foxminded.university.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.services.CoursesServiceImpl;
import ua.com.foxminded.university.services.interfaces.CoursesService;
import java.util.List;

@RestController
@Api(tags = "Courses API")
@RequestMapping("api/courses")
public class CoursesRESTController {

    private static final String DELETE_MESSAGE = "Course with id %d was deleted";
    private CoursesService coursesServiceImpl;

    @Autowired
    public CoursesRESTController(CoursesServiceImpl coursesServiceImpl) {
        this.coursesServiceImpl = coursesServiceImpl;
    }

    @GetMapping()
    @ApiOperation(value = "Show information about all courses in database",
                  response = Course.class)
    private List<Course> showAllCourses() {
        List<Course> courses = coursesServiceImpl.readTable();
        return courses;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get info about one course from DB by course id",
                  response = Course.class)
    public Course getCourse(@PathVariable int id){
        Course course = coursesServiceImpl.readOneRecordFromTable(id);
        return course;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete course from database by id",
                  response = Course.class)
    public String deleteCourse(@PathVariable int id) {
        coursesServiceImpl.deleteCourseById(id);

        return String.format(DELETE_MESSAGE, id);
    }

    @PostMapping()
    @ApiOperation(value = "Create new course",
                  response = Course.class)
    public Course createCourse(@RequestBody Course course) {
        coursesServiceImpl.createCourse(course);
        return course;
    }

    @PutMapping()
    @ApiOperation(value = "Update course data",
                  response = Course.class)
    public Course updateGroup(@RequestBody Course course) {
        coursesServiceImpl.createCourse(course);
        return course;
    }
}
