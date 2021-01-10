package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.models.GroupCourse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupsCoursesMapper implements RowMapper<GroupCourse> {

    @Override
    public GroupCourse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new GroupCourse(
            rs.getInt("group_id"),
            rs.getInt("course_id")
        );
    }
}
