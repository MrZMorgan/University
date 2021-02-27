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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class StudentsControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testShowAllStudents() throws Exception {
        String studentsControllerRequestMapping = "/students";
        String view = "students/all-students";
        String firstStudentName = "Egor";
        String firstStudentLastname = "Anchutin";
        String secondStudentName = "Guinea";
        String secondStudentLastname = "Pig";

        this.mockMvc.perform(get(studentsControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view))
                .andExpect(content().string(allOf(
                        containsString(firstStudentName),
                        containsString(firstStudentLastname),
                        containsString(secondStudentName),
                        containsString(secondStudentLastname))));
    }

    @Test
    void deleteStudent() throws Exception {
        String studentsControllerRequestMapping = "/students/1";
        String view = "redirect:/students";

        mockMvc.perform(delete(studentsControllerRequestMapping))
                .andExpect(view().name(view))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void createStudent() throws Exception {
        String studentsControllerRequestMapping = "/students/new";
        String view = "students/new";

        mockMvc.perform(get(studentsControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view));

        studentsControllerRequestMapping = "/students";
        view = "students/all-students";

        mockMvc.perform(get(studentsControllerRequestMapping))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(view));
    }

    @Test
    void editStudent() throws Exception {
        String studentsControllerRequestMapping = "/students/2/edit";
        String view = "students/edit";

        mockMvc.perform(get(studentsControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view));
    }
}