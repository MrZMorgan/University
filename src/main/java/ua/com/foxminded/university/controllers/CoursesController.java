package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.services.CoursesService;

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
    private String showAllCourses(Model model) {
        List<Course> courses = coursesService.readTable();
        model.addAttribute("allCourses", courses);

        return "courses/all-courses";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable int id) {
        coursesService.deleteCourseById(id);
        return "redirect:/courses";
    }

    @GetMapping("/new")
    public String newCourse(@ModelAttribute("course") Course course) {
        return "courses/new";
    }

    @PostMapping()
    public String createCourse(@ModelAttribute("course") Course course) {
        coursesService.createCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/{id}/edit")
    public String editCourse(Model model, @PathVariable("id") int id) {
        model.addAttribute("course", coursesService.readOneRecordFromTable(id));
        return "courses/edit";
    }

    @PatchMapping("/{id}")
    public String updateGroup(@ModelAttribute("course") Course course,
                              @PathVariable("id") int id) {
        coursesService.updateCourseData(id, course);
        return "redirect:/courses";
    }
}
