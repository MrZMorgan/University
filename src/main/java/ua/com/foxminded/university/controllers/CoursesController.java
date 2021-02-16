package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.services.CoursesServiceImpl;
import ua.com.foxminded.university.services.interfaces.CoursesService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    private CoursesService coursesServiceImpl;

    @Autowired
    public CoursesController(CoursesServiceImpl coursesServiceImpl) {
        this.coursesServiceImpl = coursesServiceImpl;
    }

    @GetMapping()
    private String showAllCourses(Model model) {
        List<Course> courses = coursesServiceImpl.readTable();
        model.addAttribute("allCourses", courses);

        return "courses/all-courses";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable int id) {
        coursesServiceImpl.deleteCourseById(id);
        return "redirect:/courses";
    }

    @GetMapping("/new")
    public String newCourse(@ModelAttribute("course") Course course) {
        return "courses/new";
    }

    @PostMapping()
    public String createCourse(@ModelAttribute("course") @Valid Course course,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "courses/new";
        }
        coursesServiceImpl.createCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/{id}/edit")
    public String editCourse(Model model, @PathVariable("id") int id) {
        model.addAttribute("course", coursesServiceImpl.readOneRecordFromTable(id));
        return "courses/edit";
    }

    @PatchMapping("/{id}")
    public String updateGroup(@ModelAttribute("course") @Valid Course course,
                              BindingResult bindingResult,
                              @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "courses/edit";
        }
        coursesServiceImpl.updateCourseData(id, course);
        return "redirect:/courses";
    }
}
