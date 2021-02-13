package ua.com.foxminded.university.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfaces.TeachersDao;
import ua.com.foxminded.university.entities.Teacher;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TeachersJdbcDao implements TeachersDao {

    private EntityManager entityManager;

    @Autowired
    public TeachersJdbcDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Teacher data) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(data);
    }

    @Override
    public Teacher read(int teacherId) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Teacher.class, teacherId);
    }

    @Override
    public List<Teacher> read() {
        Session session = entityManager.unwrap(Session.class);
        Query<Teacher> query = session.createQuery("from Teacher", Teacher.class);
        List<Teacher> teachers = query.getResultList();
        return teachers;
    }

    @Override
    public void update(int id, Teacher teacherForQuery) {
        Teacher teacher = read(id);
        teacher.setId(teacherForQuery.getId());
        teacher.setFirstName(teacherForQuery.getFirstName());
        teacher.setLastName(teacherForQuery.getLastName());
        teacher.setCourses(teacherForQuery.getCourses());
        teacher.setAge(teacherForQuery.getAge());

        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(teacher);
    }

    @Override
    public void delete(int teacherId) {
        Session session = entityManager.unwrap(Session.class);
        Teacher teacher = session.get(Teacher.class, teacherId);
        session.delete(teacher);
    }
}
