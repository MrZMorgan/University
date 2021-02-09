package ua.com.foxminded.university.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.dao.TeachersJdbcDao;
import ua.com.foxminded.university.entities.Teacher;
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
        teacherService.deleteTeacherFromCourse(courseIdToDelete);
        verify(coursesJdbcDao, times(1)).deleteTeacherFromCourse(courseIdToDelete);
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

    @Test
    void shouldReadOneRecordFromTable() {
        int teacherId = anyInt();
        teacherService.readOneRecordFromTable(teacherId);
        verify(teachersJdbcDao, times(1)).read(teacherId);
    }

    @Test
    void shouldReadTable() {
        teacherService.readTable();
        verify(teachersJdbcDao, times(1)).read();
    }

    @Test
    void shouldUpdateTeacherData() {
        int id = 1;
        Teacher teacherMock = mock(Teacher.class);
        teacherService.updateTeacherData(id, teacherMock);
        verify(teachersJdbcDao, times(1)).update(id, teacherMock);
    }
}