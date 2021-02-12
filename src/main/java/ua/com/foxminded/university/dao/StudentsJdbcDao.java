package ua.com.foxminded.university.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfaces.StudentsDao;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;

import javax.persistence.EntityManager;
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
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(data);
    }

    @Override
    public Student read(int studentId) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Student.class, studentId);
    }

    public List<Student> readStudentsRelatedToGroup(int groupId) {
        Session session = entityManager.unwrap(Session.class);
        Group group = session.get(Group.class, groupId);
        return group.getStudents();
    }

    public List<Student> readStudentsRelatedToCourse(int courseId) {
        Session session = entityManager.unwrap(Session.class);
        Course course = session.get(Course.class, courseId);
        return course.getStudents();
    }

    @Override
    public List<Student> read() {
        Session session = entityManager.unwrap(Session.class);
        Query<Student> query = session.createQuery("from Student", Student.class);
        List<Student> students = query.getResultList();
        return students;
    }

    @Override
    public void update(int id, Student studentForQuery) {
        Student student = read(id);
        student.setId(studentForQuery.getId());
        student.setFirstName(studentForQuery.getFirstName());
        student.setLastName(studentForQuery.getLastName());
        student.setAge(studentForQuery.getAge());
        student.setGroup(studentForQuery.getGroup());
        student.setCourses(studentForQuery.getCourses());

        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(student);
    }

    public void deleteStudentsFromGroup(int groupId) {
        Session session = entityManager.unwrap(Session.class);
        Group group = session.get(Group.class, groupId);
        group.setStudents(null);
        session.saveOrUpdate(group);
    }

    @Override
    public void delete(int studentId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Student> query = session.createQuery("delete from Student where id =:studentId")
                .setParameter("studentId", studentId);
        query.executeUpdate();
    }

    public void deleteStudentFromAllCourses(int studentId) {
        Student student = read(studentId);
        student.setCourses(null);
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(student);
    }

    public void changeStudentGroup(int studentId, int groupId) {
        Session session = entityManager.unwrap(Session.class);
        Student student = read(studentId);
        student.setGroup(session.get(Group.class, groupId));
        session.saveOrUpdate(student);
    }

    public void assignStudentToCourse(int studentId, int courseId) {
        Session session = entityManager.unwrap(Session.class);
        Student student = read(studentId);
        student.getCourses().add(session.get(Course.class, courseId));
        session.saveOrUpdate(student);
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        Session session = entityManager.unwrap(Session.class);
        Student student = read(studentId);
        student.getCourses().remove(session.get(Course.class, courseId));
        session.saveOrUpdate(student);
    }

    public void updateStudentId(int studentId, int updatedId) {
        Student student = read(studentId);
        student.setId(updatedId);
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(student);
    }
}
