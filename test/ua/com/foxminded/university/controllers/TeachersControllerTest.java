package ua.com.foxminded.university.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.foxminded.university.Application;
import ua.com.foxminded.university.H2JpaConfig;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class, H2JpaConfig.class})
@ActiveProfiles("test")
class TeachersControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testShowAllTeacher() throws Exception {
        String teacherControllerRequestMapping = "/teachers";
        String view = "teachers/all-teachers";

        String firstTeacherName = "Ivan";
        String firstTeacherLastname = "Smirnov";
        String secondTeacherName = "Oleg";
        String secondTeacherLastname = "Sidorov";

        this.mockMvc.perform(get(teacherControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view))
                .andExpect(content().string(allOf(
                        containsString(firstTeacherName),
                        containsString(firstTeacherLastname),
                        containsString(secondTeacherName),
                        containsString(secondTeacherLastname))));
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
        String teacherControllerRequestMapping = "/teachers/2/edit";
        String view = "teachers/edit";

        mockMvc.perform(get(teacherControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view));

    }
}