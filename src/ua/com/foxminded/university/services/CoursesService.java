package ua.com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.models.Course;

@Service
public class CoursesService {

    private CoursesJdbcDao coursesJdbcDao;

    @Autowired
    public CoursesService(CoursesJdbcDao coursesJdbcDao) {
        this.coursesJdbcDao = coursesJdbcDao;
    }

    public void deleteCourseById(int courseId) {
        coursesJdbcDao.delete(courseId);
    }

    public void createCourse(Course course) {
        coursesJdbcDao.create(course);
    }

    public void renameCourse(int courseIdToRename, String newCourseName) {
        coursesJdbcDao.renameCourse(courseIdToRename, newCourseName);
    }
}
