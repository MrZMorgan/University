package ua.com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.entities.Course;

import java.util.List;

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

    public Course readOneRecordFromTable(int courseId) {
        return coursesJdbcDao.read(courseId);
    }

    public List<Course> readTable() {
        return coursesJdbcDao.read();
    }

    public List<Course> readCoursesRelatedToTeacher(int teacherId) {
        return coursesJdbcDao.readCoursesRelatedToTeacher(teacherId);
    }

    public List<Course> readCoursesByStudentId(int studentId) {
        return coursesJdbcDao.readCoursesByStudentId(studentId);
    }

    public void updateCourseData(int id, Course courseForQuery) {
        coursesJdbcDao.update(id, courseForQuery);
    }

    public void updateCourseId(int courseId, int updatedId, int group_id) {
        coursesJdbcDao.updateCourseId(courseId, updatedId, group_id);
    }
}
