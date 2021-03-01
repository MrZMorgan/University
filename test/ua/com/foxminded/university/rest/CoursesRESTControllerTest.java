package ua.com.foxminded.university.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.foxminded.university.entities.Course;;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.services.CoursesServiceImpl;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;
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

        mockMvc.perform(get("http://localhost:8080/api/courses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(expectedCoursesList.get(0).getName())))
                .andExpect(jsonPath("$[0].teacher.firstName",
                        is((expectedCoursesList.get(0).getTeacher().getFirstName()))))
                .andExpect(jsonPath("$[1].name", is(expectedCoursesList.get(1).getName())))
                .andExpect(jsonPath("$[1].teacher.firstName",
                        is((expectedCoursesList.get(1).getTeacher().getFirstName()))));
    }

    @Test
    void deleteCourse() {
    }

    @Test
    void createCourse() {
    }

    @Test
    void updateGroup() {
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