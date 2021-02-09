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
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class StudentsJdbcDaoTest {

    @Autowired
    private StudentsJdbcDao studentsJdbcDao;
    @Autowired
    private GroupsJdbcDao groupsJdbcDao;

    @Test
    @Transactional
    void shouldCreateStudent() {
        Group groupForTest = groupsJdbcDao.read(1);

        String firstNameForTest = "firstName";
        String lastNameForTest = "lastName";
        int ageForTest = 30;

        studentsJdbcDao.create(new Student(firstNameForTest, lastNameForTest, ageForTest, groupForTest));

        int expectedTableSize = 5;
        int actualTableSize = studentsJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    @Transactional
    void shouldReadStudent() {
        List<Course> coursesForTest = new LinkedList<>();

        String student1FirstName = "Egor";
        String student1LastName = "Anchutin";
        int student1Age = 20;
        int student1GroupId = 1;
        Group student1group = groupsJdbcDao.read(student1GroupId);

        Student expectedStudent = new Student(student1FirstName, student1LastName, student1Age, student1group);
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
    @Transactional
    void shouldUpdateStudent() {
        Group groupForTest = groupsJdbcDao.read(1);
        List<Course> coursesForTest = new LinkedList<>();

        String firstNameForTest = "Egor";
        String lastNameForTest = "Anchutin";
        int ageForTest = 30;

        Student studentToUpdate = new Student(firstNameForTest, lastNameForTest, ageForTest, groupForTest);
        studentsJdbcDao.update(1, studentToUpdate);

        Student actualUpdatedStudent = studentsJdbcDao.read(1);
        Student expectedUpdatedStudent = new Student(firstNameForTest, lastNameForTest, ageForTest, groupForTest);

        assertEquals(expectedUpdatedStudent, actualUpdatedStudent);
    }

    @Test
    @Transactional
    void shouldDeleteStudent() {
        int studentIdToDelete = 2;

        studentsJdbcDao.delete(studentIdToDelete);

        int expectedTableSize = 3;
        int actualTableSize = studentsJdbcDao.read().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    private List<Student> createStudentsListForTest() {
        List<Student> students = new LinkedList<>();

        List<Course> coursesForTest = new LinkedList<>();

        String student1FirstName = "Egor";
        String student1LastName = "Anchutin";
        int student1Age = 20;
        int student1GroupId = 1;
        Group student1group = groupsJdbcDao.read(student1GroupId);
        Student student1 = new Student(student1FirstName, student1LastName, student1Age, student1group);

        String student2FirstName = "Guinea";
        String student2LastName = "Pig";
        int student2Age = 21;
        int student2GroupId = 1;
        Group student2group = groupsJdbcDao.read(student2GroupId);
        Student student2 = new Student(student2FirstName, student2LastName, student2Age, student2group);

        String student3FirstName = "Olga";
        String student3LastName = "Petrova";
        int student3Age = 23;
        int student3GroupId = 2;
        Group student3group = groupsJdbcDao.read(student3GroupId);
        Student student3 = new Student(student3FirstName, student3LastName, student3Age, student3group);

        String student4FirstName = "Yaroslava";
        String student4LastName = "Igorevna";
        int student4Age = 24;
        int student4GroupId = 2;
        Group student4group = groupsJdbcDao.read(student4GroupId);
        Student student4 = new Student(student4FirstName, student4LastName, student4Age, student4group);

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);

        return students;
    }
}