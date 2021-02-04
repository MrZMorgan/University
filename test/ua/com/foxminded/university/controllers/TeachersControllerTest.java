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
import ua.com.foxminded.university.config.AppSpringConfig;
import ua.com.foxminded.university.config.TestConfig;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
class TeachersControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void testShowAllTeacher() throws Exception {
        String teacherControllerRequestMapping = "/teachers";
        String view = "teachers/all-teachers";
        String teacherNameFromPage = "Oleg";

        this.mockMvc.perform(get(teacherControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view))
                .andExpect(content().string(containsString(teacherNameFromPage)));
    }

    @Test
    void deleteStudent() throws Exception {
        String teacherControllerRequestMapping = "/teachers/1";
        String view = "redirect:/teachers";

        mockMvc.perform(delete(teacherControllerRequestMapping))
                .andExpect(view().name(view))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void createStudent() throws Exception {
        String teacherControllerRequestMapping = "/teachers/new";
        String view = "teachers/new";

        mockMvc.perform(get(teacherControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view));

        teacherControllerRequestMapping = "/teachers";
        view = "teachers/all-teachers";

        mockMvc.perform(get(teacherControllerRequestMapping))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(view));
    }

    @Test
    void editStudent() throws Exception {
        String teacherControllerRequestMapping = "/teachers/1/edit";
        String view = "teachers/edit";

        mockMvc.perform(get(teacherControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view));
    }
}