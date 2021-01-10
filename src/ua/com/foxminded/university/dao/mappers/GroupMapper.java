package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.StudentJdbcDao;
import ua.com.foxminded.university.models.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<Group> {

    private final StudentJdbcDao studentJdbcDao = new StudentJdbcDao(new JdbcTemplate());

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Group(
                rs.getInt("id"),
                rs.getString("name"),
                studentJdbcDao.readStudentsRelatedToGroup(rs.getInt("id"))
        );
    }
}
