package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfacers.GroupsDao;
import ua.com.foxminded.university.dao.mappers.GroupMapper;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.Group;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class GroupsJdbcDao implements GroupsDao {

    public static final String CREATE = "INSERT INTO groups (name) VALUES (?)";
    public static final String READ = "SELECT FROM groups WHERE id = ?";
    public static final String READ_ALL = "SELECT * FROM groups";
    public static final String READ_GROUPS_RELATED_TO_COURSES = "SELECT * FROM groups " +
                                                                 "JOIN groups_courses ON groups.id = groups_courses.course_id " +
                                                                 "WHERE course_id = ?";
    public static final String UPDATE = "UPDATE groups SET name = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM groups WHERE id = ?";
    public static final String DAO_EXCEPTION_MESSAGE = "There is no group with this ID in the database";
    public static final String READ_GROUP_BY_STUDENT_ID = "SELECT groups.id, name FROM groups " +
                                                          "JOIN students s on groups.id = s.group_id " +
                                                          "WHERE s.id = ?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupsJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Object[] data) {
        jdbcTemplate.update(CREATE, data[0]);
    }

    public Group read(int groupId) {
        Group group = jdbcTemplate.query(READ, new Object[]{groupId}, new GroupMapper(jdbcTemplate))
                .stream()
                .findAny()
                .orElse(null);

        if (group == null) {
            try {
                throw new DAOException(DAO_EXCEPTION_MESSAGE);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

        return group;
    }

    public Group readGroupByStudentId(int studentId) {
        Group group = jdbcTemplate.query(READ_GROUP_BY_STUDENT_ID, new Object[]{studentId}, new GroupMapper(jdbcTemplate))
                .stream()
                .findAny()
                .orElse(null);

        if (group == null) {
            try {
                throw new DAOException(DAO_EXCEPTION_MESSAGE);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

        return group;
    }

    public List<Group> read() {
        return jdbcTemplate.query(READ_ALL, new GroupMapper(jdbcTemplate));
    }

    public List<Group> readGroupsRelatedToCourse(int courseId) {
        return jdbcTemplate.query(READ_GROUPS_RELATED_TO_COURSES,
                new Object[]{courseId}, new GroupMapper(jdbcTemplate));
    }

    public void update(int id, Group groupForQuery) {
        jdbcTemplate.update(UPDATE,
                groupForQuery.getGroupName(),
                id);
    }

    public void delete(int groupId) {
        jdbcTemplate.update(DELETE, groupId);
    }
}
