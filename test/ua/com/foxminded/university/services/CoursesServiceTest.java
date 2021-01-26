package ua.com.foxminded.university.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.dao.*;
import ua.com.foxminded.university.models.Course;
import static org.mockito.Mockito.*;

class CoursesServiceTest {
    private CoursesJdbcDao coursesJdbcDaoMock;
    private CoursesService coursesService;

    @BeforeEach
    void setUp() {
        coursesJdbcDaoMock = mock(CoursesJdbcDao.class);
        coursesService = new CoursesService(coursesJdbcDaoMock);

    }

    @Test
    void shouldDeleteCourseById() {
        int courseId = anyInt();

        coursesService.deleteCourseById(courseId);
        verify(coursesJdbcDaoMock, times(1)).delete(courseId);
    }

    @Test
    void shouldCreateCourse() {
        Course courseMock = mock(Course.class);
        coursesService.createCourse(courseMock);

        verify(coursesJdbcDaoMock, times(1)).create(courseMock);
    }

    @Test
    void shouldRenameCourse() {
        int courseId = anyInt();
        String newCourseName = anyString();
        coursesService.renameCourse(courseId, newCourseName);

        verify(coursesJdbcDaoMock, times(1)).renameCourse(courseId, newCourseName);
    }
}