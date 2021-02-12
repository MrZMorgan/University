package ua.com.foxminded.university.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfaces.CoursesDao;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.entities.Course;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CoursesJdbcDao implements CoursesDao {

    private EntityManager entityManager;

    @Autowired
    public CoursesJdbcDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Course data) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(data);
    }

    @Override
    public Course read(int courseId) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Course.class, courseId);
    }

    @Override
    public List<Course> read() {
        Session session = entityManager.unwrap(Session.class);
        Query<Course> query = session.createQuery("from Course", Course.class);
        List<Course> courses = query.getResultList();
        return courses;
    }

    public List<Course> readCoursesRelatedToTeacher(int teacherId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Course> query = session.createQuery("from Course where teacher.id =:teacherId")
                .setParameter("teacherId", teacherId);
        List<Course> courses = query.getResultList();
        return courses;
    }

    public List<Course> readCoursesByStudentId(int studentId) {
        Session session = entityManager.unwrap(Session.class);
        Student student = session.get(Student.class, studentId);
        return student.getCourses();
    }

    @Override
    public void update(int id, Course courseForQuery) {
        Session session = entityManager.unwrap(Session.class);
        Course course = session.get(Course.class, id);
        course.setId(courseForQuery.getId());
        course.setName(courseForQuery.getName());
        course.setTeacher(courseForQuery.getTeacher());
        course.setGroups(courseForQuery.getGroups());
        course.setStudents(courseForQuery.getStudents());
        session.saveOrUpdate(course);
    }

    public void deleteTeacherFromAllCourses(int teacherId) {
        Session session = entityManager.unwrap(Session.class);
        Teacher teacher = session.get(Teacher.class, teacherId);
        teacher.setCourses(null);
        session.saveOrUpdate(teacher);
    }

    @Override
    public void delete(int courseId) {
        Session session = entityManager.unwrap(Session.class);
        Course course = session.get(Course.class, courseId);
        session.delete(course);
    }

    public void renameCourse(int courseIdToRename, String newCourseName) {
        Session session = entityManager.unwrap(Session.class);
        Course course = session.get(Course.class, courseIdToRename);
        course.setName(newCourseName);
        session.saveOrUpdate(course);
    }

    public void deleteTeacherFromCourse(int courseId) {
        Session session = entityManager.unwrap(Session.class);
        Course course = session.get(Course.class, courseId);
        course.setTeacher(null);
        session.saveOrUpdate(course);
    }

    public void assignTeacherToCourse(int teacherId, int courseId) {
        Session session = entityManager.unwrap(Session.class);
        Course course = session.get(Course.class, courseId);
        Teacher teacher = session.get(Teacher.class, teacherId);
        course.setTeacher(teacher);
        session.saveOrUpdate(course);
    }

    public void updateCourseId(int courseId, int updatedId) {
        Session session = entityManager.unwrap(Session.class);
        Course course = session.get(Course.class, courseId);
        course.setId(updatedId);
        session.saveOrUpdate(course);
    }
}
