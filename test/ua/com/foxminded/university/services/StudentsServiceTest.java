package ua.com.foxminded.university.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.entities.Student;

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

    @Test
    void shouldReadOneRecordFromTable() {
        int studentId = anyInt();
        studentsService.readOneRecordFromTable(studentId);
        verify(studentsJdbcDao, times(1)).read(studentId);
    }

    @Test
    void shouldReadStudentsRelatedToGroup() {
        int groupId = anyInt();
        studentsService.readStudentsRelatedToGroup(groupId);
        verify(studentsJdbcDao, times(1)).readStudentsRelatedToGroup(groupId);
    }

    @Test
    void shouldReadStudentsRelatedToCourse() {
        int courseId = anyInt();
        studentsService.readStudentsRelatedToCourse(courseId);
        verify(studentsJdbcDao, times(1)).readStudentsRelatedToCourse(courseId);
    }

    @Test
    void shouldReadTable() {
        studentsService.readTable();
        verify(studentsJdbcDao, times(1)).read();
    }

    @Test
    void shouldUpdateStudentData() {
        int id = 1;
        Student studentMock = mock(Student.class);
        studentsService.updateStudentData(id, studentMock);
        verify(studentsJdbcDao, times(1)).update(id, studentMock);
    }

    @Test
    void shouldUpdateStudentId() {
        int studentId = anyInt();
        int updatedId = anyInt();
        studentsService.updateStudentId(studentId, updatedId);
        verify(studentsJdbcDao, times(1)).updateStudentId(studentId, updatedId);
    }
}