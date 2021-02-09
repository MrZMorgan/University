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
        int id = 1;
        String firstNameForTest = "Ivan";
        String lastNameForTest = "Smirnov";
        int ageForTest = 20;
        List<Course> coursesListForTest = new LinkedList<>();

        Teacher expectedTeacher = new Teacher(firstNameForTest, lastNameForTest, ageForTest);
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

    private List<Teacher> createTeachersListForTest() {
        List<Teacher> teachers = new LinkedList<>();

        int teacher1Id = 1;
        String teacher1FirstName = "Ivan";
        String teacher1LastName = "Smirnov";
        int teacher1Age = 20;
        List<Course> teacher1CourseList = new LinkedList<>();
        Teacher teacher1 = new Teacher(teacher1FirstName, teacher1LastName, teacher1Age);

        int teacher2Id = 2;
        String teacher2FirstName = "Oleg";
        String teacher2LastName = "Sidorov";
        int teacher2Age = 25;
        List<Course> teacher2CourseList = new LinkedList<>();
        Teacher teacher2 = new Teacher(teacher2FirstName, teacher2LastName, teacher2Age);

        teachers.add(teacher1);
        teachers.add(teacher2);

        return teachers;
    }
}