package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.models.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<Group> {

    private final StudentsJdbcDao studentsJdbcDao = new StudentsJdbcDao();

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Group(
                rs.getInt("id"),
                rs.getString("name"),
                studentsJdbcDao.readStudentsRelatedToGroup(rs.getInt("id"))
        );
    }
}
