package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfaces.TeachersDao;
import ua.com.foxminded.university.entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        entityManager.merge(data);
    }

    @Override
    public Teacher read(int teacherId) {
        return entityManager.find(Teacher.class, teacherId);
    }

    @Override
    public List<Teacher> read() {
        Query query = entityManager.createQuery("from Teacher", Teacher.class);
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

        entityManager.merge(teacher);
    }

    @Override
    public void delete(int teacherId) {
        Query query = entityManager.createQuery("delete from Teacher where id=:teacherId");
        query.setParameter("teacherId", teacherId);
        query.executeUpdate();
    }
}
