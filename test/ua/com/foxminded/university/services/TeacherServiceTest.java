package ua.com.foxminded.university.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.dao.TeachersJdbcDao;
import ua.com.foxminded.university.models.Teacher;

import static org.mockito.Mockito.*;

class TeacherServiceTest {

    private TeachersJdbcDao teachersJdbcDao;
    private CoursesJdbcDao coursesJdbcDao;
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        teachersJdbcDao = mock(TeachersJdbcDao.class);
        coursesJdbcDao = mock(CoursesJdbcDao.class);
        teacherService = new TeacherService(coursesJdbcDao, teachersJdbcDao);
    }

    @Test
    void shouldDeleteTeacherById() {
        int teacherId = anyInt();
        teacherService.deleteTeacherById(teacherId);
        verify(teachersJdbcDao, times(1)).delete(teacherId);
    }

    @Test
    void shouldDeleteTeacherFromCourse() {
        int teacherId = anyInt();
        int courseIdToDelete = anyInt();
        teacherService.deleteTeacherFromCourse(teacherId, courseIdToDelete);
        verify(coursesJdbcDao, times(1)).deleteTeacherFromCourse(teacherId, courseIdToDelete);
    }

    @Test
    void shouldAssignTeacherToCourse() {
        int teacherId = anyInt();
        int courseId = anyInt();
        teacherService.assignTeacherToCourse(teacherId, courseId);
        verify(coursesJdbcDao, times(1)).assignTeacherToCourse(teacherId, courseId);
    }

    @Test
    void shouldCreateTeacher() {
        Teacher teacherMock = mock(Teacher.class);
        teacherService.createTeacher(teacherMock);
        verify(teachersJdbcDao, times(1)).create(teacherMock);
    }

    @Test
    void shouldDeleteTeacherFromAllCourses() {
        int teacherId = anyInt();
        teacherService.deleteTeacherFromAllCourses(teacherId);
        verify(coursesJdbcDao, times(1)).deleteTeacherFromAllCourses(teacherId);
    }
}