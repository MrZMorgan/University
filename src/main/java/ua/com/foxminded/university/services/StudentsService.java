package ua.com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.entities.Student;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StudentsService {

    private StudentsJdbcDao studentsJdbcDao;

    @Autowired
    public StudentsService(StudentsJdbcDao studentsJdbcDao) {
        this.studentsJdbcDao = studentsJdbcDao;
    }

    @Transactional
    public void deleteStudentById(int studentId) {
        studentsJdbcDao.delete(studentId);
    }

    @Transactional
    public void transferStudentToAnotherGroup(int studentId, int groupId) {
        studentsJdbcDao.changeStudentGroup(studentId, groupId);
    }

    @Transactional
    public void deleteStudentFromGroup(int groupId) {
        studentsJdbcDao.deleteStudentsFromGroup(groupId);
    }

    @Transactional
    public void assignStudentToCourse(int studentId, int courseId) {
        studentsJdbcDao.assignStudentToCourse(studentId, courseId);
    }

    @Transactional
    public void deleteStudentFromCourse(int studentId, int courseId) {
        studentsJdbcDao.deleteStudentFromCourse(studentId, courseId);
    }

    @Transactional
    public void deleteStudentsFromAllCourses(int studentId) {
        studentsJdbcDao.deleteStudentFromAllCourses(studentId);
    }

    @Transactional
    public void createStudent(Student student) {
        studentsJdbcDao.create(student);
    }

    @Transactional
    public Student readOneRecordFromTable(int studentId) {
        return studentsJdbcDao.read(studentId);
    }

    @Transactional
    public List<Student> readStudentsRelatedToGroup(int groupId) {
        return studentsJdbcDao.readStudentsRelatedToGroup(groupId);
    }

    @Transactional
    public List<Student> readStudentsRelatedToCourse(int courseId) {
        return studentsJdbcDao.readStudentsRelatedToCourse(courseId);
    }

    @Transactional
    public List<Student> readTable() {
        return studentsJdbcDao.read();
    }

    @Transactional
    public void updateStudentData(int id, Student studentForQuery) {
        studentsJdbcDao.update(id, studentForQuery);
    }

    @Transactional
    public void updateStudentId(int studentId, int updatedId) {
        studentsJdbcDao.updateStudentId(studentId, updatedId);
    }
}
