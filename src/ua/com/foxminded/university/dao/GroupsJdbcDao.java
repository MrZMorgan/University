package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.mappers.CourseMapper;
import ua.com.foxminded.university.dao.mappers.GroupMapper;
import ua.com.foxminded.university.models.Course;
import ua.com.foxminded.university.models.Group;

import java.util.List;

public class GroupsJdbcDao {
    private final JdbcTemplate jdbcTemplate;

    public GroupsJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(String groupName) {
        jdbcTemplate.update("INSERT INTO groups (name) VALUES (?)", groupName);
    }

    public Group read(int courseId) {
        return jdbcTemplate.query("SELECT * FROM courses WHERE id = ?", new Object[]{courseId}, new GroupMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public List<Group> read() {
        return jdbcTemplate.query("SELECT * FROM students", new GroupMapper());
    }

    public List<Group> readGroupsRelatedToCourse(int courseId) {
        return jdbcTemplate.query(
                "SELECT name FROM groups " +
                    "JOIN groups_courses ON groups.id = groups_courses.id " +
                    "WHERE course_id = ?",
                new Object[]{courseId}, new GroupMapper());
    }

    public void update(int id, Group groupForQuery) {
        jdbcTemplate.update("UPDATE group SET name=? WHERE id=?",
                groupForQuery.getGroupName(),
                id);
    }

    public void delete(int groupId) {
        jdbcTemplate.update("DELETE FROM groups WHERE id=?", groupId);
    }
}
