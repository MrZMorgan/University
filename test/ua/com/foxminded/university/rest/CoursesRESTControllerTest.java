package ua.com.foxminded.university.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.com.foxminded.university.entities.Course;;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.services.CoursesServiceImpl;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CoursesRESTController.class)
@ActiveProfiles("test")
class CoursesRESTControllerTest {

    @MockBean
    private CoursesServiceImpl coursesServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldShowAllCourses() throws Exception {
        List<Course> expectedCoursesList = expectedCoursesList();
        when(coursesServiceMock.readTable()).thenReturn(expectedCoursesList);
        String URL = "http://localhost:8080/api/courses";

        mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(expectedCoursesList.get(0).getName())))
                .andExpect(jsonPath("$[0].teacher.firstName",
                        is(expectedCoursesList.get(0).getTeacher().getFirstName())))
                .andExpect(jsonPath("$[1].name", is(expectedCoursesList.get(1).getName())))
                .andExpect(jsonPath("$[1].teacher.firstName",
                        is(expectedCoursesList.get(1).getTeacher().getFirstName())));
    }

    @Test
    void shouldGetCourse() throws Exception {
        Course expectedCourse = expectedFirstCourse();
        int coursesId = 1;
        String URL = String.format("http://localhost:8080/api/courses/%d", coursesId);

        when(coursesServiceMock.readOneRecordFromTable(coursesId)).thenReturn(expectedCourse);

        mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(expectedCourse.getName())))
                .andExpect(jsonPath("$.teacher.firstName", is(expectedCourse.getTeacher().getFirstName())))
                .andExpect(jsonPath("$.teacher.lastName", is(expectedCourse.getTeacher().getLastName())));
    }

    @Test
    void shouldDeleteCourse() throws Exception {
        int coursesId = 1;
        String URL = String.format("http://localhost:8080/api/courses/%d", coursesId);
        String message = String.format("Course with id %d was deleted", coursesId);

        mockMvc.perform(delete(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(message)));
    }

    @Test
    void shouldCreateCourse() throws Exception {
        String URL = "http://localhost:8080/api/courses";

        String inputJson = "{" +
                "\"id\":3," +
                "\"name\":\"math\"," +
                "\"teacher\":null," +
                "\"groups\":[]," +
                "\"students\":[]" +
                "}";

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(inputJson, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void shouldUpdateGroup() throws Exception {
        String URL = "http://localhost:8080/api/courses";

        String inputJson = "{" +
                "\"id\":1," +
                "\"name\":\"economy\"," +
                "\"teacher\":null," +
                "\"groups\":[]," +
                "\"students\":[]" +
                "}";

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(inputJson, mvcResult.getResponse().getContentAsString());
    }

    List<Course> expectedCoursesList() {
        List<Course> courses = new ArrayList<>();
        courses.add(expectedFirstCourse());
        courses.add(expectedSecondCourse());
        return courses;
    }

    Course expectedFirstCourse() {
        Teacher teacherForTest = new Teacher("Ivan", "Smirnov", 20);
        Course expectedCourse = new Course("math", teacherForTest);
        return expectedCourse;
    }

    Course expectedSecondCourse() {
        Teacher teacherForTest = new Teacher("Oleg", "Sidorov", 25);

        Course expectedCourse = new Course("economy", teacherForTest);
        return expectedCourse;
    }
}