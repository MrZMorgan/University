package ua.com.foxminded.university.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.services.TeacherServiceImpl;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeachersRESTController.class)
@ActiveProfiles("test")
class TeachersRESTControllerTest {

    @MockBean
    private TeacherServiceImpl teacherServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldShowAllStudents() throws Exception {
        List<Teacher> expectedTeachersList = createTeachersListForTest();
        Teacher firstTeacher = expectedTeachersList.get(0);
        Teacher secondTeacher = expectedTeachersList.get(1);

        when(teacherServiceMock.readTable()).thenReturn(expectedTeachersList);
        String URL = "http://localhost:8080/api/teachers";

        mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(firstTeacher.getId())))
                .andExpect(jsonPath("$[0].firstName", is(firstTeacher.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(firstTeacher.getLastName())))
                .andExpect(jsonPath("$[0].age", is(firstTeacher.getAge())))
                .andExpect(jsonPath("$[1].id", is(secondTeacher.getId())))
                .andExpect(jsonPath("$[1].firstName", is(secondTeacher.getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(secondTeacher.getLastName())))
                .andExpect(jsonPath("$[1].age", is(secondTeacher.getAge())));
    }

    @Test
    void shouldGetTeacher() throws Exception {
        Teacher expectedTeacher = firstExpectedTeacher();
        int teacherId = 1;
        String URL = String.format("http://localhost:8080/api/teachers/%d", teacherId);

        when(teacherServiceMock.readOneRecordFromTable(teacherId)).thenReturn(expectedTeacher);

        mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedTeacher.getId())))
                .andExpect(jsonPath("$.firstName", is(expectedTeacher.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedTeacher.getLastName())))
                .andExpect(jsonPath("$.age", is(expectedTeacher.getAge())));
    }

    @Test
    void shouldDeleteTeacher() throws Exception {
        int teacherId = 1;
        String URL = String.format("http://localhost:8080/api/teachers/%d", teacherId);
        String message = String.format("Teacher with id %d was deleted", teacherId);

        mockMvc.perform(delete(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(message)));
    }

    @Test
    void shouldCreateTeacher() throws Exception {
        String URL = "http://localhost:8080/api/teachers";

        String inputJson = "{" +
                "\"id\":3," +
                "\"firstName\":\"TestName\"," +
                "\"lastName\":\"TestLastName\"," +
                "\"age\":30," +
                "\"courses\":[]" +
                "}";

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(inputJson, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void shouldUpdateTeacher() throws Exception {
        String URL = "http://localhost:8080/api/teachers";

        String inputJson = "{" +
                "\"id\":1," +
                "\"firstName\":\"TestName\"," +
                "\"lastName\":\"TestLastName\"," +
                "\"age\":30," +
                "\"courses\":[]" +
                "}";

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(inputJson, mvcResult.getResponse().getContentAsString());
    }

    private Teacher firstExpectedTeacher() {
        int teacherId = 1;
        String teacherFirstName = "Ivan";
        String teacher1LastName = "Smirnov";
        int teacherAge = 20;
        Teacher teacher = new Teacher(teacherFirstName, teacher1LastName, teacherAge);
        teacher.setId(teacherId);

        return teacher;
    }

    private List<Teacher> createTeachersListForTest() {
        List<Teacher> teachers = new ArrayList<>();

        Teacher teacher1 = firstExpectedTeacher();

        int teacher2Id = 2;
        String teacher2FirstName = "Oleg";
        String teacher2LastName = "Sidorov";
        int teacher2Age = 25;
        Teacher teacher2 = new Teacher(teacher2FirstName, teacher2LastName, teacher2Age);
        teacher2.setId(teacher2Id);

        teachers.add(teacher1);
        teachers.add(teacher2);

        return teachers;
    }
}