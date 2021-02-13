package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfaces.GroupsDao;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.entities.Group;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        entityManager.merge(data);
    }

    @Override
    public Group read(int groupId) {
        return entityManager.find(Group.class, groupId);
    }

    public Group readGroupByStudentId(int studentId) {
        Student student = entityManager.find(Student.class, studentId);
        Group group = student.getGroup();
        return group;
    }

    @Override
    public List<Group> read() {
        Query query = entityManager.createQuery("from Group", Group.class);
        List<Group> groups = query.getResultList();
        return groups;
    }

    public List<Group> readGroupsRelatedToCourse(int courseId) {
        Course course = entityManager.find(Course.class, courseId);
        return course.getGroups();
    }

    @Override
    public void update(int id, Group groupForQuery) {
        Group group = entityManager.find(Group.class, id);
        group.setId(groupForQuery.getId());
        group.setName(groupForQuery.getName());
        group.setStudents(groupForQuery.getStudents());
        entityManager.merge(group);
    }

    @Override
    public void delete(int groupId) {
        Query query = entityManager.createQuery("delete from Group where id=:groupId");
        query.setParameter("groupId", groupId);
        query.executeUpdate();
    }

    public void renameGroup(int groupIdToRename, String newGroupName) {
        Group group = entityManager.find(Group.class, groupIdToRename);
        group.setName(newGroupName);
        entityManager.merge(group);
    }

    public void assignGroupToCourse(int groupId, int courseId) {
        Group group = entityManager.find(Group.class, groupId);
        Course course = entityManager.find(Course.class, courseId);
        course.getGroups().add(group);
        entityManager.merge(course);
    }
}
