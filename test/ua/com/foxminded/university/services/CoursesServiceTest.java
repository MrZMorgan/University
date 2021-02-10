package ua.com.foxminded.university.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.dao.*;
import ua.com.foxminded.university.entities.Course;
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

    @Test
    void shouldReadOneRecordFromTable() {
        int courseId = anyInt();
        coursesService.readOneRecordFromTable(courseId);
        verify(coursesJdbcDaoMock, times(1)).read(courseId);
    }

    @Test
    void shouldReadTable() {
        coursesService.readTable();
        verify(coursesJdbcDaoMock, times(1)).read();
    }

    @Test
    void shouldReadCoursesRelatedToTeacher() {
        int teacherId = anyInt();
        coursesService.readCoursesRelatedToTeacher(teacherId);
        verify(coursesJdbcDaoMock, times(1)).readCoursesRelatedToTeacher(teacherId);
    }

    @Test
    void shouldReadCoursesByStudentId() {
        int studentId = anyInt();
        coursesService.readCoursesByStudentId(studentId);
        verify(coursesJdbcDaoMock, times(1)).readCoursesByStudentId(studentId);
    }

    @Test
    void shouldUpdateCourseData() {
        int id = 1;
        Course courseMock = mock(Course.class);
        coursesService.updateCourseData(id, courseMock);
        verify(coursesJdbcDaoMock, times(1)).update(id, courseMock);
    }

    @Test
    void shouldUpdateCourseId() {
        int courseId = anyInt();
        int updatedId = anyInt();
        coursesService.updateCourseId(courseId, updatedId);
        verify(coursesJdbcDaoMock, times(1)).updateCourseId(courseId, updatedId);
    }
}