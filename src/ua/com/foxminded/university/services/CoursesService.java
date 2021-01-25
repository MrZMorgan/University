package ua.com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.dao.GroupsCoursesJdbcDao;
import ua.com.foxminded.university.dao.StudentsCoursesJdbcDao;
import ua.com.foxminded.university.models.Course;

@Service
public class CoursesService {

    private StudentsCoursesJdbcDao studentsCoursesJdbcDao;
    private GroupsCoursesJdbcDao groupsCoursesJdbcDao;
    private CoursesJdbcDao coursesJdbcDao;

    @Autowired
    public CoursesService(StudentsCoursesJdbcDao studentsCoursesJdbcDao,
                          GroupsCoursesJdbcDao groupsCoursesJdbcDao,
                          CoursesJdbcDao coursesJdbcDao) {
        this.studentsCoursesJdbcDao = studentsCoursesJdbcDao;
        this.groupsCoursesJdbcDao = groupsCoursesJdbcDao;
        this.coursesJdbcDao = coursesJdbcDao;
    }

    public void deleteCourseById(int courseId) {
        studentsCoursesJdbcDao.deleteCourse(courseId);
        groupsCoursesJdbcDao.deleteCourse(courseId);
        coursesJdbcDao.delete(courseId);
    }

    public void createCourse(Course course) {
        coursesJdbcDao.create(course);
    }

    public void renameCourse(int courseIdToRename, String newCourseName) {
        coursesJdbcDao.renameCourse(courseIdToRename, newCourseName);
    }
}
