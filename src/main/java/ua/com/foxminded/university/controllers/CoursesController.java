package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.university.models.Course;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.services.CoursesService;
import ua.com.foxminded.university.services.GroupsService;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    private CoursesService coursesService;

    @Autowired
    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping()
    private String showAllGroups(Model model) {
        List<Course> courses = coursesService.readTable();
        model.addAttribute("allCourses", courses);

        return "courses/all-courses";
    }

    @GetMapping("/{id}")
    public String deleteCourse(@PathVariable int id) {
        coursesService.deleteCourseById(id);
        return "redirect:/courses";
    }
}
