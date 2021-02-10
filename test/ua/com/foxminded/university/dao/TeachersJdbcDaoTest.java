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
class TeachersJdbcDaoTest {

    @Autowired
    private TeachersJdbcDao teachersJdbcDao;
    @Autowired
    private CoursesJdbcDao coursesJdbcDao;

    @Test
    @Transactional
    void shouldCreateTeacher() {
        String firstNameForTest = "firstName";
        String lastNameForTest = "lastName";
        int ageForTest = 30;

        teachersJdbcDao.create(new Teacher(firstNameForTest, lastNameForTest, ageForTest));

        int expectedTableSize = 3;
        int actualTableSize = teachersJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    @Transactional
    void shouldReadTeacher() {
        Teacher expectedTeacher = firstExpectedTeacher();
        Teacher actualTeacher = teachersJdbcDao.read(1);

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    @Transactional
    void shouldReadAllTeachers() {
        List<Teacher> actualStudentsList = teachersJdbcDao.read();
        List<Teacher> expectedStudentsList = createTeachersListForTest();

        assertEquals(expectedStudentsList, actualStudentsList);
    }

    @Test
    @Transactional
    void shouldUpdateTeacher() {
        String firstNameForTest = "Ivan";
        String lastNameForTest = "Smirnov";
        int ageForTest = 28;

        Teacher teacherToUpdate = new Teacher(firstNameForTest, lastNameForTest, ageForTest);
        teachersJdbcDao.update(1, teacherToUpdate);

        Teacher actualUpdatedTeacher = teachersJdbcDao.read(1);
        Teacher expectedUpdatedTeacher = new Teacher(firstNameForTest, lastNameForTest, ageForTest);

        System.out.println(actualUpdatedTeacher);
        System.out.println(expectedUpdatedTeacher);

        assertEquals(expectedUpdatedTeacher, actualUpdatedTeacher);
    }

    @Test
    @Transactional
    void shouldDeleteTeachers() {
        int teacherIdToDelete = 2;

        teachersJdbcDao.delete(teacherIdToDelete);

        int expectedTableSize = 1;
        int actualTableSize = teachersJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    private Teacher firstExpectedTeacher() {
        int teacherId = 1;
        String teacherFirstName = "Ivan";
        String teacher1LastName = "Smirnov";
        int teacherAge = 20;
        List<Course> teacherCourseList = coursesJdbcDao.readCoursesRelatedToTeacher(teacherId);
        Teacher teacher = new Teacher(teacherFirstName, teacher1LastName, teacherAge);
        teacher.setId(teacherId);
        teacher.setCourses(teacherCourseList);

        return teacher;
    }

    private List<Teacher> createTeachersListForTest() {
        List<Teacher> teachers = new LinkedList<>();

        Teacher teacher1 = firstExpectedTeacher();

        int teacher2Id = 2;
        String teacher2FirstName = "Oleg";
        String teacher2LastName = "Sidorov";
        int teacher2Age = 25;
        List<Course> teacher2CourseList = coursesJdbcDao.readCoursesRelatedToTeacher(teacher2Id);
        Teacher teacher2 = new Teacher(teacher2FirstName, teacher2LastName, teacher2Age);
        teacher2.setId(2);
        teacher2.setCourses(teacher2CourseList);

        teachers.add(teacher1);
        teachers.add(teacher2);

        return teachers;
    }
}