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
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.services.StudentsServiceImpl;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentsRESTController.class)
@ActiveProfiles("test")
class StudentsRESTControllerTest {

    @MockBean
    private StudentsServiceImpl studentsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldShowAllStudents() throws Exception {
        List<Student> expectedStudentsList = createListOfAllStudentsForTest();
        when(studentsService.readTable()).thenReturn(expectedStudentsList);
        String URL = "http://localhost:8080/api/students";

        mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(expectedStudentsList.get(0).getId())))
                .andExpect(jsonPath("$[0].firstName", is(expectedStudentsList.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(expectedStudentsList.get(0).getLastName())))
                .andExpect(jsonPath("$[0].group.id",
                        is(expectedStudentsList.get(0).getGroup().getId())))
                .andExpect(jsonPath("$[0].group.name",
                        is(expectedStudentsList.get(0).getGroup().getName())))
                .andExpect(jsonPath("$[3].id", is(expectedStudentsList.get(3).getId())))
                .andExpect(jsonPath("$[3].firstName", is(expectedStudentsList.get(3).getFirstName())))
                .andExpect(jsonPath("$[3].lastName", is(expectedStudentsList.get(3).getLastName())))
                .andExpect(jsonPath("$[3].group.id",
                        is(expectedStudentsList.get(3).getGroup().getId())))
                .andExpect(jsonPath("$[3].group.name",
                        is(expectedStudentsList.get(3).getGroup().getName())));

    }

    @Test
    void shouldGetStudent() throws Exception {
        Student expectedStudent = firstExpectedStudent();
        int studentId = 1;
        String URL = String.format("http://localhost:8080/api/students/%d", studentId);

        when(studentsService.readOneRecordFromTable(studentId)).thenReturn(expectedStudent);

        mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedStudent.getId())))
                .andExpect(jsonPath("$.firstName", is(expectedStudent.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedStudent.getLastName())))
                .andExpect(jsonPath("$.group.id", is(expectedStudent.getGroup().getId())))
                .andExpect(jsonPath("$.group.name", is(expectedStudent.getGroup().getName())));
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        int studentId = 1;
        String URL = String.format("http://localhost:8080/api/students/%d", studentId);
        String message = String.format("Student with id %d was deleted", studentId);

        mockMvc.perform(delete(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(message)));
    }

    @Test
    void shouldCreateStudent() throws Exception {
        String URL = "http://localhost:8080/api/students";

        String inputJson = "{" +
                "\"id\":5," +
                "\"firstName\":\"TestName\"," +
                "\"lastName\":\"TestLastName\"," +
                "\"age\":30," +
                "\"group\":null," +
                "\"courses\":[]" +
                "}";

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(inputJson, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        String URL = "http://localhost:8080/api/students";

        String inputJson = "{" +
                "\"id\":1," +
                "\"firstName\":\"TestName\"," +
                "\"lastName\":\"TestLastName\"," +
                "\"age\":30," +
                "\"group\":null," +
                "\"courses\":[]" +
                "}";

        MvcResult mvcResult = mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(inputJson, mvcResult.getResponse().getContentAsString());
    }

    private Student firstExpectedStudent() {
        String student1FirstName = "Egor";
        String student1LastName = "Anchutin";
        int student1Age = 20;
        String groupName = "GS-10-1";
        Group student1group = new Group(groupName);
        student1group.setId(1);
        Student student1 = new Student(student1FirstName, student1LastName, student1Age, student1group);
        student1.setId(1);

        return student1;
    }

    private Student secondExpectedStudent() {
        String student2FirstName = "Guinea";
        String student2LastName = "Pig";
        int student2Age = 21;
        String groupName = "GS-10-1";
        Group student2group = new Group(groupName);
        student2group.setId(1);
        Student student2 = new Student(student2FirstName, student2LastName, student2Age, student2group);
        student2.setId(2);

        return student2;
    }

    private List<Student> createListOfAllStudentsForTest() {
        List<Student> students = new ArrayList<>();

        String student3FirstName = "Olga";
        String student3LastName = "Petrova";
        int student3Age = 23;
        String group3Name = "GS-10-1";
        Group student3group = new Group(group3Name);
        student3group.setId(2);
        Student student3 = new Student(student3FirstName, student3LastName, student3Age, student3group);
        student3.setId(3);

        String student4FirstName = "Yaroslava";
        String student4LastName = "Igorevna";
        int student4Age = 24;
        String group4Name = "ERB-11-2";
        Group student4group = new Group(group4Name);
        student4group.setId(2);
        Student student4 = new Student(student4FirstName, student4LastName, student4Age, student4group);
        student4.setId(4);

        students.add(firstExpectedStudent());
        students.add(secondExpectedStudent());
        students.add(student3);
        students.add(student4);

        return students;
    }
}