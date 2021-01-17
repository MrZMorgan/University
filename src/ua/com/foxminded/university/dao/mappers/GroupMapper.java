package ua.com.foxminded.university.dao.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.models.Group;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<Group> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {

        StudentsJdbcDao studentsJdbcDao = new StudentsJdbcDao(jdbcTemplate);

        return new Group(
                rs.getInt("id"),
                rs.getString("name"),
                studentsJdbcDao.readStudentsRelatedToGroup(rs.getInt("id"))
        );
    }
}
