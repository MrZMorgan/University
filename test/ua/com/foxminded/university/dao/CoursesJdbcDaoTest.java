package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.foxminded.university.config.TestConfig;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Teacher;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class CoursesJdbcDaoTest {

    private final static String COURSE_NAME_FOR_TEST = "chemistry";
    @Autowired
    private CoursesJdbcDao coursesJdbcDao;
    @Autowired
    private TeachersJdbcDao teachersJdbcDao;

    @Test
    @Transactional
    void shouldCreateCourse() {
        Teacher teacher = teachersJdbcDao.read(1);
        coursesJdbcDao.create(new Course(COURSE_NAME_FOR_TEST, teacher));

        int expectedTableSize = 3;
        int actualTableSize = coursesJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    @Transactional
    void shouldReadCourse() {
        Teacher teacherForTest = teachersJdbcDao.read(1);

        Course actualCourse = coursesJdbcDao.read(1);
        Course expectedCourse = new Course("math", teacherForTest);

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    @Transactional
    void shouldReadAllCourses() {
        List<Course> actualCoursesList = coursesJdbcDao.read();
        List<Course> expectedCoursesList = new LinkedList<>();

        String firstCourseName = "math";
        String secondCourseName = "economy";

        Teacher teacherForMath = teachersJdbcDao.read(1);
        Teacher teacherForEconomy = teachersJdbcDao.read(2);

        expectedCoursesList.add(new Course(firstCourseName, teacherForMath));
        expectedCoursesList.add(new Course(secondCourseName, teacherForEconomy));

        assertEquals(expectedCoursesList, actualCoursesList);
    }

    @Test
    @Transactional
    void shouldUpdateCourse() {
        Teacher teacher = teachersJdbcDao.read(1);
        Course courseToUpdate = new Course(COURSE_NAME_FOR_TEST, teacher);
        coursesJdbcDao.update(2, courseToUpdate);

        Course actualUpdatedCourse = coursesJdbcDao.read(2);
        Course expectedUpdatedCourse = new Course(COURSE_NAME_FOR_TEST, teacher);

        assertEquals(expectedUpdatedCourse, actualUpdatedCourse);
    }

    @Test
    @Transactional
    void shouldDeleteCourse() {
        int courseIdToDelete = 2;

        coursesJdbcDao.delete(courseIdToDelete);

        int expectedTableSize = 1;
        int actualTableSize = coursesJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }
}