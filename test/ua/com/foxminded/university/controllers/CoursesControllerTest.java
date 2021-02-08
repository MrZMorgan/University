package ua.com.foxminded.university.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.foxminded.university.config.TestConfig;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.services.CoursesService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
class CoursesControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private CoursesService coursesService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testShowAllCourses() throws Exception {
        String courseControllerRequestMapping = "/courses";
        String view = "courses/all-courses";

        this.mockMvc.perform(get(courseControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view));
    }

    @Test
    void deleteCourse() throws Exception {
        String courseControllerRequestMapping = "/courses/1";
        String view = "redirect:/courses";

        mockMvc.perform(delete(courseControllerRequestMapping))
                .andExpect(view().name(view))
                .andExpect(status().is3xxRedirection())
        ;
    }

    @Test
    void createCourse() throws Exception {
        String courseControllerRequestMapping = "/courses/new";
        String view = "courses/new";

        mockMvc.perform(get(courseControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view));

        courseControllerRequestMapping = "/courses";
        view = "courses/all-courses";

        mockMvc.perform(get(courseControllerRequestMapping))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(view));
    }

    @Test
    void editCourse() throws Exception {
        String courseControllerRequestMapping = "/courses/2/edit";
        String view = "courses/edit";
        String expectedModelAttributeName = "course";

        Course expectedCourse = coursesService.readOneRecordFromTable(2);

        mockMvc.perform(get(courseControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(model().attribute(expectedModelAttributeName, expectedCourse))
                .andExpect(view().name(view));
    }
}