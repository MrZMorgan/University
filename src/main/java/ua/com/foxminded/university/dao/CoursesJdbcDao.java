package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfaces.CoursesDao;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.entities.Course;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        entityManager.merge(data);
    }

    @Override
    public Course read(int courseId) {
        return entityManager.find(Course.class, courseId);
    }

    @Override
    public List<Course> read() {
        Query query = entityManager.createQuery("from Course", Course.class);
        List<Course> courses = query.getResultList();
        return courses;
    }

    public List<Course> readCoursesRelatedToTeacher(int teacherId) {
        Teacher teacher = entityManager.find(Teacher.class, teacherId);
        List<Course> courses = teacher.getCourses();
        return courses;
    }

    public List<Course> readCoursesByStudentId(int studentId) {
        Student student = entityManager.find(Student.class, studentId);
        return student.getCourses();
    }

    @Override
    public void update(int id, Course courseForQuery) {
        Course course = entityManager.find(Course.class, id);
        course.setId(courseForQuery.getId());
        course.setName(courseForQuery.getName());
        course.setTeacher(courseForQuery.getTeacher());
        course.setGroups(courseForQuery.getGroups());
        course.setStudents(courseForQuery.getStudents());
        entityManager.merge(course);
    }

    public void deleteTeacherFromAllCourses(int teacherId) {
        Teacher teacher = entityManager.find(Teacher.class, teacherId);
        List<Course> courses = teacher.getCourses();
        courses.clear();
        entityManager.merge(teacher);
    }

    @Override
    public void delete(int courseId) {
        Query query = entityManager.createQuery("delete from Course where id=:courseId");
        query.setParameter("courseId", courseId);
        query.executeUpdate();
    }

    public void renameCourse(int courseIdToRename, String newCourseName) {
        Course course = entityManager.find(Course.class, courseIdToRename);
        course.setName(newCourseName);
        entityManager.merge(course);
    }

    public void deleteTeacherFromCourse(int courseId) {
        Course course = entityManager.find(Course.class, courseId);
        course.setTeacher(null);
        entityManager.merge(course);
    }

    public void assignTeacherToCourse(int teacherId, int courseId) {
        Course course = entityManager.find(Course.class, courseId);
        Teacher teacher = entityManager.find(Teacher.class, teacherId);
        course.setTeacher(teacher);
        entityManager.merge(course);
    }

    public void updateCourseId(int courseId, int updatedId) {
        Course course = entityManager.find(Course.class, courseId);
        course.setId(updatedId);
        entityManager.merge(course);
    }
}
