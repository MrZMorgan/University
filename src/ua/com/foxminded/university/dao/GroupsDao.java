package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class GroupsDao {
    private final JdbcTemplate jdbcTemplate;

    public GroupsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create() {
        jdbcTemplate.update("INSERT INTO groups (name) VALUES (?)", "Группа");
    }
}
