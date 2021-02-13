package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfaces.StudentsDao;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class StudentsJdbcDao implements StudentsDao {

    private EntityManager entityManager;

    @Autowired
    public StudentsJdbcDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Student data) {
        entityManager.merge(data);
    }

    @Override
    public Student read(int studentId) {
        return entityManager.find(Student.class, studentId);
    }

    public List<Student> readStudentsRelatedToGroup(int groupId) {
        Group group = entityManager.find(Group.class, groupId);
        return group.getStudents();
    }

    public List<Student> readStudentsRelatedToCourse(int courseId) {
        Course course = entityManager.find(Course.class, courseId);
        return course.getStudents();
    }

    @Override
    public List<Student> read() {
        Query query = entityManager.createQuery("from Student", Student.class);
        List<Student> students = query.getResultList();
        return students;
    }

    @Override
    public void update(int id, Student studentForQuery) {
        Student student = entityManager.find(Student.class, id);
        student.setId(studentForQuery.getId());
        student.setFirstName(studentForQuery.getFirstName());
        student.setLastName(studentForQuery.getLastName());
        student.setAge(studentForQuery.getAge());
        student.setGroup(studentForQuery.getGroup());
        student.setCourses(studentForQuery.getCourses());

        entityManager.merge(student);
    }

    public void deleteStudentsFromGroup(int groupId) {
        Group group = entityManager.find(Group.class, groupId);
        group.getStudents().clear();
        entityManager.merge(group);
    }

    @Override
    public void delete(int studentId) {
        Query query = entityManager.createQuery("delete from Student where id =:studentId");
        query.setParameter("studentId", studentId);
        query.executeUpdate();
    }

    public void deleteStudentFromAllCourses(int studentId) {
        Student student = read(studentId);
        student.getCourses().clear();
        entityManager.merge(student);
    }

    public void changeStudentGroup(int studentId, int groupId) {
        Student student = entityManager.find(Student.class, studentId);
        student.setGroup(entityManager.find(Group.class, groupId));
        entityManager.merge(student);
    }

    public void assignStudentToCourse(int studentId, int courseId) {
        Student student = entityManager.find(Student.class, studentId);
        student.getCourses().add(entityManager.find(Course.class, courseId));
        entityManager.merge(student);
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        Student student = read(studentId);
        student.getCourses().remove(entityManager.find(Course.class, courseId));
        entityManager.merge(student);
    }

    public void updateStudentId(int studentId, int updatedId) {
        Student student = entityManager.find(Student.class, studentId);
        student.setId(updatedId);
        entityManager.merge(student);
    }
}
