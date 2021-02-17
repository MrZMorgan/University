package ua.com.foxminded.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.services.CoursesServiceImpl;
import ua.com.foxminded.university.services.interfaces.CoursesService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CoursesController {

    private CoursesService coursesServiceImpl;

    @Autowired
    public CoursesController(CoursesServiceImpl coursesServiceImpl) {
        this.coursesServiceImpl = coursesServiceImpl;
    }

    @GetMapping()
    private ModelAndView showAllCourses(Model model) {
        List<Course> courses = coursesServiceImpl.readTable();
        model.addAttribute("allCourses", courses);
        return new ModelAndView("courses/all-courses", "courses", courses);
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteCourse(@PathVariable int id) {
        coursesServiceImpl.deleteCourseById(id);
        return new ModelAndView("redirect:/courses");
    }

    @GetMapping("/new")
    public ModelAndView newCourse(@ModelAttribute("course") @RequestBody Course course) {
        return new ModelAndView("courses/new", "course", course);
    }

    @PostMapping()
    public ModelAndView createCourse(@ModelAttribute("course") @RequestBody @Valid Course course,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("courses/new", "course", course);
        }
        coursesServiceImpl.createCourse(course);
        return new ModelAndView("redirect:/courses", "course", course);
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editCourse(Model model, @PathVariable("id") int id) {
        Course course = coursesServiceImpl.readOneRecordFromTable(id);
        model.addAttribute("course", coursesServiceImpl.readOneRecordFromTable(id));
        return new ModelAndView("courses/edit", "course", course);
    }

    @PatchMapping("/{id}")
    public ModelAndView updateGroup(@ModelAttribute("course") @RequestBody @Valid Course course,
                                    BindingResult bindingResult,
                                    @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("courses/edit", "courses", course);
        }
        coursesServiceImpl.updateCourseData(id, course);
        return new ModelAndView("redirect:/courses", "courses", course);
    }
}
