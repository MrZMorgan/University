package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.interfaces.CoursesDao;
import ua.com.foxminded.university.dao.mappers.CourseMapper;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.Course;
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
    public final static String UPDATE_COURSE = "UPDATE course SET name=?, teacher_id=? WHERE id=?";
    public final static String DELETE_COURSE_BY_ID = "DELETE FROM course WHERE id=?";
    public final static String DAO_EXCEPTION_MESSAGE = "There is no course with this ID in the database";


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

    public List<Course> readCoursesByStudentId(int teacherId) {
        return jdbcTemplate.query(
                READ_COURSES_BY_STUDENT_ID,
                new Object[]{teacherId}, new CourseMapper(jdbcTemplate));
    }

    @Override
    public void update(int id, Course courseForQuery) {
        jdbcTemplate.update(UPDATE_COURSE,
                courseForQuery.getCourseName(),
                courseForQuery.getTeacher().getId(),
                id);
    }

    @Override
    public void delete(int courseId) {
        jdbcTemplate.update(DELETE_COURSE_BY_ID, courseId);
    }
}
