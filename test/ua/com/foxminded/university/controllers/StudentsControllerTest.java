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
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.services.StudentsService;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
class StudentsControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private StudentsService studentsService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void testShowAllStudents() throws Exception {
        String studentsControllerRequestMapping = "/students";
        String view = "students/all-students";

        this.mockMvc.perform(get(studentsControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view));
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
    @Transactional
    void editStudent() throws Exception {
        String studentsControllerRequestMapping = "/students/2/edit";
        String view = "students/edit";

        String expectedModelAttributeName = "student";
        Student expectedStudent = studentsService.readOneRecordFromTable(2);

        mockMvc.perform(get(studentsControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(model().attribute(expectedModelAttributeName, expectedStudent))
                .andExpect(view().name(view));
    }
}