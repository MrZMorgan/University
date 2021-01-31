package ua.com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.dao.TeachersJdbcDao;
import ua.com.foxminded.university.models.Teacher;
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

    public void createTeacher(Teacher teacher) {
        teachersJdbcDao.create(teacher);
    }

    public void deleteTeacherById(int teacherId) {
        teachersJdbcDao.delete(teacherId);
    }

    public void deleteTeacherFromCourse(int teacherId, int courseId) {
        coursesJdbcDao.deleteTeacherFromCourse(teacherId, courseId);
    }

    public void assignTeacherToCourse(int teacherId, int courseId) {
        coursesJdbcDao.assignTeacherToCourse(teacherId, courseId);
    }

    public void deleteTeacherFromAllCourses(int teacherId) {
        coursesJdbcDao.deleteTeacherFromAllCourses(teacherId);
    }

    public Teacher readOneRecordFromTable(int teacherId) {
        return teachersJdbcDao.read(teacherId);
    }

    public List<Teacher> readTable() {
        return teachersJdbcDao.read();
    }

    public void updateTeacherData(int id, Teacher teacherForQuery) {
        teachersJdbcDao.update(id, teacherForQuery);
    }
}
