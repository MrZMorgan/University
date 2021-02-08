package ua.com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.entities.Student;
import java.util.List;

@Service
public class StudentsService {

    private StudentsJdbcDao studentsJdbcDao;

    @Autowired
    public StudentsService(StudentsJdbcDao studentsJdbcDao) {
        this.studentsJdbcDao = studentsJdbcDao;
    }

    public void deleteStudentById(int studentId) {
        studentsJdbcDao.delete(studentId);
    }

    public void transferStudentToAnotherGroup(int studentId, int groupId) {
        studentsJdbcDao.changeStudentGroup(studentId, groupId);
    }

    public void deleteStudentFromGroup(int groupId) {
        studentsJdbcDao.deleteStudentsFromGroup(groupId);
    }

    public void assignStudentToCourse(int studentId, int courseId) {
        studentsJdbcDao.assignStudentToCourse(studentId, courseId);
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        studentsJdbcDao.deleteStudentFromCourse(studentId, courseId);
    }

    public void deleteStudentsFromAllCourses(int studentId) {
        studentsJdbcDao.deleteStudentFromAllCourses(studentId);
    }

    public void createStudent(Student student) {
        studentsJdbcDao.create(student);
    }

    public Student readOneRecordFromTable(int studentId) {
        return studentsJdbcDao.read(studentId);
    }

    public List<Student> readStudentsRelatedToGroup(int groupId) {
        return studentsJdbcDao.readStudentsRelatedToGroup(groupId);
    }
    public List<Student> readStudentsRelatedToCourse(int courseId) {
        return studentsJdbcDao.readStudentsRelatedToCourse(courseId);
    }

    public List<Student> readTable() {
        return studentsJdbcDao.read();
    }

    public void updateStudentData(int id, Student studentForQuery) {
        studentsJdbcDao.update(id, studentForQuery);
    }

    public void updateStudentId(int studentId, int updatedId) {
        studentsJdbcDao.updateStudentId(studentId, updatedId);
    }
}
