package ua.com.foxminded.university.dao.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.models.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GroupMapper implements RowMapper<Group> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        StudentsJdbcDao studentsJdbcDao = new StudentsJdbcDao(jdbcTemplate);

        int groupId = rs.getInt("id");
        String groupName = rs.getString("name");
        List<Student> students = new LinkedList<>();

        return new Group(groupId, groupName, students);
    }
}
