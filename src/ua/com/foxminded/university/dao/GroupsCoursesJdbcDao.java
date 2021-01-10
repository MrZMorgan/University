package ua.com.foxminded.university.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.mappers.GroupsCoursesMapper;
import ua.com.foxminded.university.models.GroupCourse;

public class GroupsCoursesJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    public GroupsCoursesJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(int groupId, int courseId) {
        jdbcTemplate.update("INSERT INTO groups_courses (group_id, course_id) VALUES (?, ?)", groupId, courseId);
    }

    public GroupCourse read(int courseId, int groupId) {
        return jdbcTemplate.query(
                "SELECT * FROM groups_courses " +
                "WHERE coures_id = ? " +
                "AND group_id = ?",
                new Object[]{courseId, groupId}, new GroupsCoursesMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public void updateCourseId(int courseId, int updatedId) {
        jdbcTemplate.update("UPDATE groups_courses SET course_id=? WHERE course_id=?",
                updatedId,
                courseId);
    }

    public void deleteCourse(int courseId) {
        jdbcTemplate.update("DELETE FROM groups_courses WHERE course_id=?", courseId);
    }

    public void deleteGroup(int groupId) {
        jdbcTemplate.update("DELETE FROM groups_courses WHERE group_id=?", groupId);
    }

    public void deleteGroupFromCourse(int groupId, int courseId) {
        jdbcTemplate.update("DELETE FROM groups_courses WHERE group_id=? AND coures_id =?", groupId, courseId);
    }
}
