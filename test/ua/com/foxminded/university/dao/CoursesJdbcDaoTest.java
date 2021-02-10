package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.foxminded.university.config.TestConfig;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.entities.Teacher;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    @Autowired
    private StudentsJdbcDao studentsJdbcDao;
    @Autowired
    private GroupsJdbcDao groupsJdbcDao;

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
        Course expectedCourse = expectedFirstGroup();
        Course actualCourse = coursesJdbcDao.read(1);

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    @Transactional
    void shouldReadAllCourses() {
        List<Course> expectedCoursesList = new ArrayList<>();
        Course firstExpectedCourse = expectedFirstGroup();
        Course secondExpectedCourse = expectedSecondGroup();
        expectedCoursesList.add(firstExpectedCourse);
        expectedCoursesList.add(secondExpectedCourse);

        List<Course> actualCoursesList = coursesJdbcDao.read();

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

    Course expectedFirstGroup() {
        Teacher teacherForTest = teachersJdbcDao.read(1);

        Student student1ForTest = studentsJdbcDao.read(1);
        Student student2ForTest = studentsJdbcDao.read(2);
        List<Student> studentsForTest = new ArrayList<>();
        studentsForTest.add(student1ForTest);
        studentsForTest.add(student2ForTest);

        Group group = groupsJdbcDao.read(1);
        List<Group> groupsForTest = new ArrayList<>();
        groupsForTest.add(group);

        Course expectedCourse = new Course("math", teacherForTest);
        expectedCourse.setId(1);
        expectedCourse.setStudents(studentsForTest);
        expectedCourse.setGroups(groupsForTest);

        return expectedCourse;
    }

    Course expectedSecondGroup() {
        Teacher teacherForTest = teachersJdbcDao.read(2);

        Student student1ForTest = studentsJdbcDao.read(3);
        Student student2ForTest = studentsJdbcDao.read(4);
        List<Student> studentsForTest = new ArrayList<>();
        studentsForTest.add(student1ForTest);
        studentsForTest.add(student2ForTest);

        Group group = groupsJdbcDao.read(2);
        List<Group> groupsForTest = new ArrayList<>();
        groupsForTest.add(group);

        Course expectedCourse = new Course("economy", teacherForTest);
        expectedCourse.setId(2);
        expectedCourse.setStudents(studentsForTest);
        expectedCourse.setGroups(groupsForTest);

        return expectedCourse;
    }
}