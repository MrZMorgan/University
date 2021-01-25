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
}
