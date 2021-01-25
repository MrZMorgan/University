package ua.com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.StudentsCoursesJdbcDao;
import ua.com.foxminded.university.dao.StudentsJdbcDao;

@Service
public class StudentsService {

    private StudentsCoursesJdbcDao studentsCoursesJdbcDao;
    private StudentsJdbcDao studentsJdbcDao;

    @Autowired
    public StudentsService(StudentsCoursesJdbcDao studentsCoursesJdbcDao,
                           StudentsJdbcDao studentsJdbcDao) {
        this.studentsCoursesJdbcDao = studentsCoursesJdbcDao;
        this.studentsJdbcDao = studentsJdbcDao;
    }

    public void deleteStudentById(int studentId) {
        studentsCoursesJdbcDao.deleteStudent(studentId);
        studentsJdbcDao.delete(studentId);
    }

    public void transferStudentToAnotherGroup(int studentId, int groupId) {
        studentsJdbcDao.changeStudentGroup(studentId, groupId);
    }

    public void deleteStudentFromGroup(int studentId) {
        studentsJdbcDao.deleteStudentFromGroup(studentId);
    }

    public void assignStudentToCourse(int studentId, int courseId) {
        studentsCoursesJdbcDao.create(studentId, courseId);
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        studentsCoursesJdbcDao.deleteStudentFromCourse(studentId, courseId);
    }

    public void deleteStudentsFromAllCourses(int studentId) {
        studentsCoursesJdbcDao.deleteStudent(studentId);
    }
}
