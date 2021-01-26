package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfaces.CoursesDao;
import ua.com.foxminded.university.dao.mappers.CourseMapper;
import ua.com.foxminded.university.dao.mappers.GroupCourseMapper;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.Course;
import ua.com.foxminded.university.models.GroupCourse;

import java.util.List;

@Repository
public class CoursesJdbcDao implements CoursesDao {

    public static final String CREATE = "INSERT INTO courses (name) VALUES (?)";
    public static final String READ = "SELECT * FROM courses WHERE id = ?";
    public static final String READ_ALL = "SELECT * FROM courses";
    public static final String READ_ALL_BY_TEACHER_ID = "SELECT * FROM courses WHERE teacher_id = ?";
    public static final String READ_COURSES_BY_STUDENT_ID = "SELECT * FROM courses" +
                                                            "JOIN students_courses sc on courses.id = sc.course_id" +
                                                            "WHERE student_id = ?";
    public final static String UPDATE_COURSE = "UPDATE courses SET name=?, teacher_id=? WHERE id=?";
    public final static String DELETE_TEACHER_FROM_All_COURSES = "UPDATE courses SET teacher_id = null WHERE teacher_id = ?";
    public final static String RENAME_COURSE = "UPDATE courses SET name = ? WHERE id = ?";
    public final static String DELETE_TEACHER_FROM_COURSE = "UPDATE courses SET teacher_id = null " +
                                                            "WHERE teacher_id = ? AND id = ?";
    public final static String ASSIGN_TEACHER_TO_COURSE = "UPDATE courses SET teacher_id = ? WHERE id = ? ";
    public final static String DELETE_COURSE_BY_ID = "DELETE FROM courses WHERE id=?";
    public static final String DELETE_COURSE_FROM_STUDENTS_COURSES = "DELETE FROM students_courses WHERE course_id=?";
    public static final String DELETE_COURSE_FROM_GROUPS_COURSES = "DELETE FROM groups_courses WHERE course_id = ?";
    public static final String UPDATE_COURSE_ID_IN_STUDENTS_COURSES_TABLE = "UPDATE students_courses SET course_id=? WHERE course_id=?";
    public static final String UPDATE_COURSE_ID_IN_GROUPS_COURSES_TABLE = "UPDATE groups_courses SET course_id = ? WHERE course_id = ? AND group_id = ?";
    public static final String READ_ONE_GROUPS_COURSE = "SELECT * FROM groups_courses " +
                                                        "WHERE course_id = ? " +
                                                        "AND group_id = ?";
    public static final String READ_ALL_GROUPS_COURSES_RELATION = "SELECT * FROM groups_courses";

    public final static String DAO_EXCEPTION_MESSAGE = "There is no course with this ID in the database";
    public final static String DAO_EXCEPTION_MESSAGE_GROUPS_COURSE = "There is no group-course with this ID in the database";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CoursesJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Course data) {
        jdbcTemplate.update(CREATE, data.getCourseName());
    }

    @Override
    public Course read(int courseId) {
        Course course = jdbcTemplate.query(READ, new Object[]{courseId}, new CourseMapper(jdbcTemplate))
                .stream()
                .findAny()
                .orElse(null);

        if (course == null) {
            try {
                throw new DAOException(DAO_EXCEPTION_MESSAGE);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

        return course;
    }

    @Override
    public List<Course> read() {
        return jdbcTemplate.query(READ_ALL, new CourseMapper(jdbcTemplate));
    }

    public List<Course> readCoursesRelatedToTeacher(int teacherId) {
        return jdbcTemplate.query(READ_ALL_BY_TEACHER_ID,
                new Object[]{teacherId}, new CourseMapper(jdbcTemplate));
    }

    public List<Course> readCoursesByStudentId(int studentId) {
        return jdbcTemplate.query(
                READ_COURSES_BY_STUDENT_ID,
                new Object[]{studentId}, new CourseMapper(jdbcTemplate));
    }

    @Override
    public void update(int id, Course courseForQuery) {
        jdbcTemplate.update(UPDATE_COURSE,
                courseForQuery.getCourseName(),
                courseForQuery.getTeacher().getId(),
                id);
    }

    public void deleteTeacherFromAllCourses(int teacherId) {
        jdbcTemplate.update(DELETE_TEACHER_FROM_All_COURSES, teacherId);
    }

    @Override
    public void delete(int courseId) {
        jdbcTemplate.update(DELETE_COURSE_FROM_GROUPS_COURSES, courseId);
        jdbcTemplate.update(DELETE_COURSE_FROM_STUDENTS_COURSES, courseId);
        jdbcTemplate.update(DELETE_COURSE_BY_ID, courseId);
    }

    public void renameCourse(int courseIdToRename, String newCourseName) {
        jdbcTemplate.update(RENAME_COURSE, newCourseName, courseIdToRename);
    }

    public void deleteTeacherFromCourse(int teacherId, int courseId) {
        jdbcTemplate.update(DELETE_TEACHER_FROM_COURSE, teacherId, courseId);
    }

    public void assignTeacherToCourse(int teacherId, int courseId) {
        jdbcTemplate.update(ASSIGN_TEACHER_TO_COURSE, teacherId, courseId);
    }

    public void updateCourseId(int courseId, int updatedId, int group_id) {
        jdbcTemplate.update(UPDATE_COURSE_ID_IN_STUDENTS_COURSES_TABLE, updatedId, courseId);
        jdbcTemplate.update(UPDATE_COURSE_ID_IN_GROUPS_COURSES_TABLE, updatedId, courseId, group_id);
    }

    public GroupCourse readOneGroupsCourse(int courseId, int groupId) {
        GroupCourse groupCourse = jdbcTemplate.query(
                READ_ONE_GROUPS_COURSE,
                new Object[]{courseId, groupId}, new GroupCourseMapper())
                .stream()
                .findAny()
                .orElse(null);

        if (groupCourse == null) {
            try {
                throw new DAOException(DAO_EXCEPTION_MESSAGE_GROUPS_COURSE);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

        return groupCourse;
    }

    public List<GroupCourse> readAllGroupsCoursesRelation() {
        return jdbcTemplate.query(READ_ALL_GROUPS_COURSES_RELATION, new GroupCourseMapper());
    }
}
