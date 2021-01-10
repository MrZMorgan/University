package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.mappers.GroupMapper;
import ua.com.foxminded.university.dao.mappers.StudentMapper;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.models.Student;

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

    public void update(int id, Group groupForQuery) {
        jdbcTemplate.update("UPDATE group SET name=? WHERE id=?",
                groupForQuery.getGroupName(),
                id);
    }

    public void delete(int groupId) {
        jdbcTemplate.update("DELETE FROM groups WHERE id=?", groupId);
    }
}
