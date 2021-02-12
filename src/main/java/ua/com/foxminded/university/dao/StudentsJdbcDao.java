package ua.com.foxminded.university.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfaces.StudentsDao;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import java.util.List;

@Repository
public class StudentsJdbcDao implements StudentsDao {


    private final SessionFactory sessionFactory;

    @Autowired
    public StudentsJdbcDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Student data) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(data);
    }

    @Override
    public Student read(int studentId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Student.class, studentId);
    }

    public List<Student> readStudentsRelatedToGroup(int groupId) {
        Session session = sessionFactory.getCurrentSession();
        Group group = session.get(Group.class, groupId);
        return group.getStudents();
    }

    public List<Student> readStudentsRelatedToCourse(int courseId) {
        Session session = sessionFactory.getCurrentSession();
        Course course = session.get(Course.class, courseId);
        return course.getStudents();
    }

    @Override
    public List<Student> read() {
        Session session = sessionFactory.getCurrentSession();
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

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(student);
    }

    public void deleteStudentsFromGroup(int groupId) {
        Session session = sessionFactory.getCurrentSession();
        Group group = session.get(Group.class, groupId);
        group.setStudents(null);
        session.saveOrUpdate(group);
    }

    @Override
    public void delete(int studentId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Student> query = session.createQuery("delete from Student where id =:studentId")
                .setParameter("studentId", studentId);
        query.executeUpdate();
    }

    public void deleteStudentFromAllCourses(int studentId) {
        Student student = read(studentId);
        student.setCourses(null);
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(student);
    }

    public void changeStudentGroup(int studentId, int groupId) {
        Session session = sessionFactory.getCurrentSession();
        Student student = read(studentId);
        student.setGroup(session.get(Group.class, groupId));
        session.saveOrUpdate(student);
    }

    public void assignStudentToCourse(int studentId, int courseId) {
        Session session = sessionFactory.getCurrentSession();
        Student student = read(studentId);
        student.getCourses().add(session.get(Course.class, courseId));
        session.saveOrUpdate(student);
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        Session session = sessionFactory.getCurrentSession();
        Student student = read(studentId);
        student.getCourses().remove(session.get(Course.class, courseId));
        session.saveOrUpdate(student);
    }

    public void updateStudentId(int studentId, int updatedId) {
        Student student = read(studentId);
        student.setId(updatedId);
        sessionFactory.getCurrentSession().saveOrUpdate(student);
    }
}
