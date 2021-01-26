package ua.com.foxminded.university.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.models.Student;

class StudentsServiceTest {

    private StudentsJdbcDao studentsJdbcDao;
    private StudentsService studentsService;

    @BeforeEach
    void setUp() {
        studentsJdbcDao = mock(StudentsJdbcDao.class);
        studentsService = new StudentsService(studentsJdbcDao);
    }

    @Test
    void shouldDeleteStudentById() {
        int studentId = anyInt();
        studentsService.deleteStudentById(studentId);

        verify(studentsJdbcDao, times(1)).delete(studentId);
    }

    @Test
    void shouldTransferStudentToAnotherGroup() {
        int studentId = anyInt();
        int newGroupId = anyInt();

        studentsService.transferStudentToAnotherGroup(studentId, newGroupId);
        verify(studentsJdbcDao, times(1)).changeStudentGroup(studentId, newGroupId);
    }

    @Test
    void shouldDeleteStudentFromGroup() {
        int groupId = anyInt();

        studentsService.deleteStudentFromGroup(groupId);
        verify(studentsJdbcDao, times(1)).deleteStudentsFromGroup(groupId);
    }

    @Test
    void shouldAssignStudentToCourse () {
        int studentId = anyInt();
        int courseId = anyInt();

        studentsService.assignStudentToCourse(studentId, courseId);
        verify(studentsJdbcDao, times(1)).assignStudentToCourse(studentId, courseId);
    }

    @Test
    void shouldDeleteStudentFromCourse() {
        int studentId = anyInt();
        int courseId = anyInt();

        studentsService.deleteStudentFromCourse(studentId, courseId);
        verify(studentsJdbcDao, times(1)).deleteStudentFromCourse(studentId, courseId);
    }

    @Test
    void shouldDeleteStudentFromAllCourses() {
        int studentId = anyInt();

        studentsService.deleteStudentsFromAllCourses(studentId);
        verify(studentsJdbcDao, times(1)).deleteStudentFromAllCourses(studentId);
    }

    @Test
    void shouldCreateStudent() {
        Student studentMock = mock(Student.class);
        studentsService.createStudent(studentMock);

        verify(studentsJdbcDao, times(1)).create(studentMock);
    }
}