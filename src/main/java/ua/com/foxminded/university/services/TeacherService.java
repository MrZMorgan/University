package ua.com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.dao.TeachersJdbcDao;
import ua.com.foxminded.university.entities.Teacher;

import javax.transaction.Transactional;
import java.util.List;

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

    @Transactional
    public void createTeacher(Teacher teacher) {
        teachersJdbcDao.create(teacher);
    }

    @Transactional
    public void deleteTeacherById(int teacherId) {
        teachersJdbcDao.delete(teacherId);
    }

    @Transactional
    public void deleteTeacherFromCourse(int courseId) {
        coursesJdbcDao.deleteTeacherFromCourse(courseId);
    }

    @Transactional
    public void assignTeacherToCourse(int teacherId, int courseId) {
        coursesJdbcDao.assignTeacherToCourse(teacherId, courseId);
    }

    @Transactional
    public void deleteTeacherFromAllCourses(int teacherId) {
        coursesJdbcDao.deleteTeacherFromAllCourses(teacherId);
    }

    @Transactional
    public Teacher readOneRecordFromTable(int teacherId) {
        return teachersJdbcDao.read(teacherId);
    }

    @Transactional
    public List<Teacher> readTable() {
        return teachersJdbcDao.read();
    }

    @Transactional
    public void updateTeacherData(int id, Teacher teacherForQuery) {
        teachersJdbcDao.update(id, teacherForQuery);
    }
}
