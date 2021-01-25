package ua.com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.dao.TeachersJdbcDao;

@Service
public class TeacherService {

    private CoursesJdbcDao coursesJdbcDao;
    private TeachersJdbcDao teachersJdbcDao;

    @Autowired
    public TeacherService(CoursesJdbcDao coursesJdbcDao,
                          TeachersJdbcDao teachersJdbcDao) {
        this.coursesJdbcDao = coursesJdbcDao;
        this.teachersJdbcDao = teachersJdbcDao;
    }

    public void deleteTeacherById(int teacherId) {
        coursesJdbcDao.deleteTeacherFromCourses(teacherId);
        teachersJdbcDao.delete(teacherId);
    }
}
