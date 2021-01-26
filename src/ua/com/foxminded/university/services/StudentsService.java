package ua.com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.models.Student;

@Service
public class StudentsService {

    private StudentsJdbcDao studentsJdbcDao;

    @Autowired
    public StudentsService(StudentsJdbcDao studentsJdbcDao) {
        this.studentsJdbcDao = studentsJdbcDao;
    }

    public void deleteStudentById(int studentId) {
        studentsJdbcDao.delete(studentId);
    }

    public void transferStudentToAnotherGroup(int studentId, int groupId) {
        studentsJdbcDao.changeStudentGroup(studentId, groupId);
    }

    public void deleteStudentFromGroup(int groupId) {
        studentsJdbcDao.deleteStudentsFromGroup(groupId);
    }

    public void assignStudentToCourse(int studentId, int courseId) {
        studentsJdbcDao.assignStudentToCourse(studentId, courseId);
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        studentsJdbcDao.deleteStudentFromCourse(studentId, courseId);
    }

    public void deleteStudentsFromAllCourses(int studentId) {
        studentsJdbcDao.deleteStudentFromAllCourses(studentId);
    }

    public void createStudent(Student student) {
        studentsJdbcDao.create(student);
    }
}
