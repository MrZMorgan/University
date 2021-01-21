package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.foxminded.university.models.Course;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.models.Student;
import ua.com.foxminded.university.models.Teacher;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeachersJdbcDaoTest {

    private EmbeddedDatabase embeddedDatabase;
    private TeachersJdbcDao teachersJdbcDao;
    private GroupsJdbcDao groupsJdbcDao;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        teachersJdbcDao = new TeachersJdbcDao(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void shouldCreateTeacher() {
        int id = 3;
        String firstNameForTest = "firstName";
        String lastNameForTest = "lastName";
        int ageForTest = 30;
        List<Course> coursesListForTest = new LinkedList<>();

        teachersJdbcDao.create(new Teacher(id, firstNameForTest, lastNameForTest, ageForTest, coursesListForTest));

        int expectedTableSize = 3;
        int actualTableSize = teachersJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void shouldReadTeacher() {
        int id = 1;
        String firstNameForTest = "firstName1";
        String lastNameForTest = "lastName1";
        int ageForTest = 20;
        List<Course> coursesListForTest = new LinkedList<>();

        Teacher expectedTeacher = new Teacher(id, firstNameForTest, lastNameForTest, ageForTest, coursesListForTest);
        Teacher actualTeacher = teachersJdbcDao.read(1);

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    void shouldReadAllTeachers() {
        List<Teacher> actualStudentsList = teachersJdbcDao.read();
        List<Teacher> expectedStudentsList = createTeachersListForTest();

        assertEquals(expectedStudentsList, actualStudentsList);
    }

    @Test
    void shouldUpdateTeacher() {
        int id = 1;
        String firstNameForTest = "firstNameForTest";
        String lastNameForTest = "lastNameForTest";
        int ageForTest = 28;
        List<Course> coursesListForTest = new LinkedList<>();

        Teacher teacherToUpdate = new Teacher(id, firstNameForTest, lastNameForTest, ageForTest, coursesListForTest);
        teachersJdbcDao.update(1, teacherToUpdate);

        Teacher actualUpdatedTeacher = teachersJdbcDao.read(1);
        Teacher expectedUpdatedTeacher = new Teacher(id, firstNameForTest, lastNameForTest, ageForTest, coursesListForTest);

        assertEquals(expectedUpdatedTeacher, actualUpdatedTeacher);
    }

    @Test
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
        String teacher1FirstName = "firstName1";
        String teacher1LastName = "lastName1";
        int teacher1Age = 20;
        List<Course> teacher1CourseList = new LinkedList<>();
        Teacher teacher1 = new Teacher(teacher1Id, teacher1FirstName, teacher1LastName, teacher1Age, teacher1CourseList);

        int teacher2Id = 2;
        String teacher2FirstName = "firstName2";
        String teacher2LastName = "lastName2";
        int teacher2Age = 25;
        List<Course> teacher2CourseList = new LinkedList<>();
        Teacher teacher2 = new Teacher(teacher2Id, teacher2FirstName, teacher2LastName, teacher2Age, teacher2CourseList);

        teachers.add(teacher1);
        teachers.add(teacher2);

        return teachers;
    }
}