package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.mappers.GroupMapper;
import ua.com.foxminded.university.models.Group;

import java.util.List;

public class GroupsJdbcDao {

    private static final String INSERT_INTO = "INSERT INTO groups (name) VALUES (?)";
    private static final String READ = "SELECT FROM groups WHERE id = ?";
    private static final String READ_ALL = "SELECT * FROM groups";
    private static final String READ_GROUPS_RELATED_TO_COURSES = "SELECT name FROM groups " +
                                                                 "JOIN groups_courses ON groups.id = groups_courses.id " +
                                                                 "WHERE course_id = ?";
    private final static String UPDATE = "UPDATE groups SET name = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM groups WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public GroupsJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(String groupName) {
        jdbcTemplate.update(INSERT_INTO, groupName);
    }

    public Group read(int courseId) {
        return jdbcTemplate.query(READ, new Object[]{courseId}, new GroupMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public List<Group> read() {
        return jdbcTemplate.query(READ_ALL, new GroupMapper());
    }

    public List<Group> readGroupsRelatedToCourse(int courseId) {
        return jdbcTemplate.query(READ_GROUPS_RELATED_TO_COURSES,
                new Object[]{courseId}, new GroupMapper());
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
