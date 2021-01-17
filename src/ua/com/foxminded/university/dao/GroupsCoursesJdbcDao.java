package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.mappers.GroupCourseMapper;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.GroupCourse;

@Repository
public class GroupsCoursesJdbcDao {

    public static final String CREATE = "INSERT INTO groups_courses (group_id, course_id) VALUES (?, ?)";
    public static final String READ = "SELECT * FROM groups_courses " +
                                                            "WHERE courses_id = ? " +
                                                            "AND group_id = ?";
    public static final String UPDATE_COURSE_ID = "UPDATE groups_courses SET course_id=? WHERE course_id=?";
    public static final String DELETE_COURSE = "DELETE FROM groups_courses WHERE course_id=?";
    public static final String DELETE_GROUP = "DELETE FROM groups_courses WHERE group_id=?";
    public static final String DELETE_ONE_RECORD = "DELETE FROM groups_courses WHERE group_id=? AND coures_id =?";
    public final static String DAO_EXCEPTION_MESSAGE = "There is no group-course with this ID in the database";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupsCoursesJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(int groupId, int courseId) {
        jdbcTemplate.update(CREATE, groupId, courseId);
    }

    public GroupCourse read(int courseId, int groupId) {
        GroupCourse groupCourse = jdbcTemplate.query(
                READ,
                new Object[]{courseId, groupId}, new GroupCourseMapper())
                .stream()
                .findAny()
                .orElse(null);

        if (groupCourse == null) {
            try {
                throw new DAOException(DAO_EXCEPTION_MESSAGE);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

        return groupCourse;
    }

    public void updateCourseId(int courseId, int updatedId) {
        jdbcTemplate.update(UPDATE_COURSE_ID, updatedId, courseId);
    }

    public void deleteCourse(int courseId) {
        jdbcTemplate.update(DELETE_COURSE, courseId);
    }

    public void deleteGroup(int groupId) {
        jdbcTemplate.update(DELETE_GROUP, groupId);
    }

    public void deleteGroupFromCourse(int groupId, int courseId) {
        jdbcTemplate.update(DELETE_ONE_RECORD, groupId, courseId);
    }
}
