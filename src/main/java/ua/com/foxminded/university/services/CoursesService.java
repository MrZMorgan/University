package ua.com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.entities.Course;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CoursesService {

    private CoursesJdbcDao coursesJdbcDao;

    @Autowired
    public CoursesService(CoursesJdbcDao coursesJdbcDao) {
        this.coursesJdbcDao = coursesJdbcDao;
    }

    @Transactional
    public void deleteCourseById(int courseId) {
        coursesJdbcDao.delete(courseId);
    }

    @Transactional
    public void createCourse(Course course) {
        coursesJdbcDao.create(course);
    }

    @Transactional
    public void renameCourse(int courseIdToRename, String newCourseName) {
        coursesJdbcDao.renameCourse(courseIdToRename, newCourseName);
    }

    @Transactional
    public Course readOneRecordFromTable(int courseId) {
        return coursesJdbcDao.read(courseId);
    }

    @Transactional
    public List<Course> readTable() {
        return coursesJdbcDao.read();
    }

    @Transactional
    public List<Course> readCoursesRelatedToTeacher(int teacherId) {
        return coursesJdbcDao.readCoursesRelatedToTeacher(teacherId);
    }

    @Transactional
    public List<Course> readCoursesByStudentId(int studentId) {
        return coursesJdbcDao.readCoursesByStudentId(studentId);
    }

    @Transactional
    public void updateCourseData(int id, Course courseForQuery) {
        coursesJdbcDao.update(id, courseForQuery);
    }

    @Transactional
    public void updateCourseId(int courseId, int updatedId) {
        coursesJdbcDao.updateCourseId(courseId, updatedId);
    }
}
