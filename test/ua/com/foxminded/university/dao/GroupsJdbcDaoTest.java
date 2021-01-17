package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import static org.junit.jupiter.api.Assertions.*;

class GroupsJdbcDaoTest {

    private EmbeddedDatabase embeddedDatabase;
    private JdbcTemplate jdbcTemplate;
    private GroupsJdbcDao groupsJdbcDao;

    @BeforeAll
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Test
    void shouldCreateGroup() {
    }

    @Test
    void shouldReadGroup() {
    }

    @Test
    void shouldReadAllGroups() {
    }

    @Test
    void shouldUpdateGroup() {
    }

    @Test
    void shouldDeleteGroup() {
    }

    @BeforeAll
    public void tearDown() {
        embeddedDatabase.shutdown();
    }
}