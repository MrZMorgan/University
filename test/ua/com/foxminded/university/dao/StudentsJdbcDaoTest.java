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
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentsJdbcDaoTest {

    private EmbeddedDatabase embeddedDatabase;
    private StudentsJdbcDao studentsJdbcDao;
    private GroupsJdbcDao groupsJdbcDao;
    private StudentsCoursesJdbcDao studentsCoursesJdbcDao;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        studentsJdbcDao = new StudentsJdbcDao(jdbcTemplate);
        groupsJdbcDao = new GroupsJdbcDao(jdbcTemplate);
        studentsJdbcDao = new StudentsJdbcDao(jdbcTemplate);
        studentsCoursesJdbcDao = new StudentsCoursesJdbcDao(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void shouldCreateStudent() {
        Group groupForTest = groupsJdbcDao.read(1);
        List<Course> coursesForTest = new LinkedList<>();

        String firstNameForTest = "firstName";
        String lastNameForTest = "lastName";
        int ageForTest = 30;

        studentsJdbcDao.create(new Student(5, firstNameForTest, lastNameForTest, ageForTest, groupForTest, coursesForTest));

        int expectedTableSize = 5;
        int actualTableSize = studentsJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void shouldReadStudent() {
        List<Course> coursesForTest = new LinkedList<>();

        String student1FirstName = "firstName1";
        String student1LastName = "lastName1";
        int student1Age = 20;
        int student1GroupId = 1;
        Group student1group = groupsJdbcDao.read(student1GroupId);

        Student expectedStudent = new Student(1, student1FirstName, student1LastName, student1Age, student1group, coursesForTest);
        Student actualStudent = studentsJdbcDao.read(1);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldReadAllStudents() {
        List<Student> actualStudentsList = studentsJdbcDao.read();
        List<Student> expectedStudentsList = createStudentsListForTest();

        assertEquals(expectedStudentsList, actualStudentsList);
    }

    @Test
    void shouldUpdateStudent() {
        Group groupForTest = groupsJdbcDao.read(1);
        List<Course> coursesForTest = new LinkedList<>();

        String firstNameForTest = "firstName";
        String lastNameForTest = "lastName";
        int ageForTest = 30;

        Student studentToUpdate = new Student(1, firstNameForTest, lastNameForTest, ageForTest, groupForTest, coursesForTest);
        studentsJdbcDao.update(1, studentToUpdate);

        Student actualUpdatedStudent = studentsJdbcDao.read(1);
        Student expectedUpdatedStudent = new Student(1, firstNameForTest, lastNameForTest, ageForTest, groupForTest, coursesForTest);

        assertEquals(expectedUpdatedStudent, actualUpdatedStudent);
    }

    @Test
    void shouldDeleteStudent() {
        int studentIdToDelete = 2;

        studentsCoursesJdbcDao.deleteStudent(studentIdToDelete);
        studentsJdbcDao.delete(studentIdToDelete);

        int expectedTableSize = 3;
        int actualTableSize = studentsJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    private List<Student> createStudentsListForTest() {
        List<Student> students = new LinkedList<>();

        List<Course> coursesForTest = new LinkedList<>();

        String student1FirstName = "firstName1";
        String student1LastName = "lastName1";
        int student1Age = 20;
        int student1GroupId = 1;
        Group student1group = groupsJdbcDao.read(student1GroupId);
        Student student1 = new Student(1, student1FirstName, student1LastName, student1Age, student1group, coursesForTest);

        String student2FirstName = "firstName2";
        String student2LastName = "lastName2";
        int student2Age = 21;
        int student2GroupId = 1;
        Group student2group = groupsJdbcDao.read(student2GroupId);
        Student student2 = new Student(2, student2FirstName, student2LastName, student2Age, student2group, coursesForTest);

        String student3FirstName = "firstName3";
        String student3LastName = "lastName3";
        int student3Age = 23;
        int student3GroupId = 2;
        Group student3group = groupsJdbcDao.read(student3GroupId);
        Student student3 = new Student(3, student3FirstName, student3LastName, student3Age, student3group, coursesForTest);

        String student4FirstName = "firstName4";
        String student4LastName = "lastName4";
        int student4Age = 24;
        int student4GroupId = 2;
        Group student4group = groupsJdbcDao.read(student4GroupId);
        Student student4 = new Student(4, student4FirstName, student4LastName, student4Age, student4group, coursesForTest);

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);

        return students;
    }
}