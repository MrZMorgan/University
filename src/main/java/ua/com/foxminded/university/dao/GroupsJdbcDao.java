package ua.com.foxminded.university.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfaces.GroupsDao;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.entities.Group;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class GroupsJdbcDao implements GroupsDao {

    private EntityManager entityManager;

    @Autowired
    public GroupsJdbcDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Group data) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(data);
    }

    @Override
    public Group read(int groupId) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Group.class, groupId);
    }

    public Group readGroupByStudentId(int studentId) {
        Session session = entityManager.unwrap(Session.class);
        Student student = session.get(Student.class, studentId);
        Group group = student.getGroup();
        return group;
    }

    @Override
    public List<Group> read() {
        Session session = entityManager.unwrap(Session.class);
        Query<Group> query = session.createQuery("from Group", Group.class);
        List<Group> groups = query.getResultList();
        return groups;
    }

    public List<Group> readGroupsRelatedToCourse(int courseId) {
        Session session = entityManager.unwrap(Session.class);
        Course course = session.get(Course.class, courseId);
        return course.getGroups();
    }

    @Override
    public void update(int id, Group groupForQuery) {
        Session session = entityManager.unwrap(Session.class);
        Group group = session.get(Group.class, id);
        group.setId(groupForQuery.getId());
        group.setName(groupForQuery.getName());
        group.setStudents(groupForQuery.getStudents());
        session.saveOrUpdate(group);
    }

    @Override
    public void delete(int groupId) {
        Session session = entityManager.unwrap(Session.class);
        Group group = session.get(Group.class, groupId);
        session.delete(group);
    }

    public void renameGroup(int groupIdToRename, String newGroupName) {
        Session session = entityManager.unwrap(Session.class);
        Group group = session.get(Group.class, groupIdToRename);
        group.setName(newGroupName);
        session.saveOrUpdate(group);
    }

    public void assignGroupToCourse(int groupId, int courseId) {
        Session session = entityManager.unwrap(Session.class);
        Group group = session.get(Group.class, groupId);
        Course course = session.get(Course.class, courseId);
        course.getGroups().add(group);
        session.saveOrUpdate(course);
    }
}
