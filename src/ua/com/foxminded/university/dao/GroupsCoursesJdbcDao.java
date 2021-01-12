package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.mappers.GroupsCoursesMapper;
import ua.com.foxminded.university.models.GroupCourse;

public class GroupsCoursesJdbcDao {

    private static final String INSERT_INTO_GROUPS_COURSES = "INSERT INTO groups_courses (group_id, course_id) VALUES (?, ?)";
    private static final String SELECT_FROM_GROUPS_COURSES= "SELECT * FROM groups_courses " +
            "WHERE courses_id = ? " +
            "AND group_id = ?";
    private static final String UPDATE_COURSE_ID = "UPDATE groups_courses SET course_id=? WHERE course_id=?";
    private static final String DELETE_COURSE = "DELETE FROM groups_courses WHERE course_id=?";
    private static final String DELETE_GROUP = "DELETE FROM groups_courses WHERE group_id=?";
    private static final String DELETE_ONE_RECORD = "DELETE FROM groups_courses WHERE group_id=? AND coures_id =?";

    private final JdbcTemplate jdbcTemplate;

    public GroupsCoursesJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(int groupId, int courseId) {
        jdbcTemplate.update(INSERT_INTO_GROUPS_COURSES, groupId, courseId);
    }

    public GroupCourse read(int courseId, int groupId) {
        return jdbcTemplate.query(
                SELECT_FROM_GROUPS_COURSES,
                new Object[]{courseId, groupId}, new GroupsCoursesMapper())
                .stream()
                .findAny()
                .orElse(null);
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
