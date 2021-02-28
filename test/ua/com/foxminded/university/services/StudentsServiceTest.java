package ua.com.foxminded.university.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.foxminded.university.Application;
import ua.com.foxminded.university.H2JpaConfig;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.services.interfaces.CoursesService;
import ua.com.foxminded.university.services.interfaces.GroupsService;
import ua.com.foxminded.university.services.interfaces.StudentsService;
import ua.com.foxminded.university.services.interfaces.TeacherService;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class, H2JpaConfig.class})
@ActiveProfiles("test")
@Transactional
class StudentsServiceTest {

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private StudentsService studentsService;

    @Test
    void shouldDeleteStudentById() {
        studentsService.deleteStudentById(1);
        int expectedStudentsSize = 3;
        int actualStudentsSize = studentsService.readTable().size();
        assertEquals(expectedStudentsSize, actualStudentsSize);
    }

    @Test
    void shouldTransferStudentToAnotherGroup() {
        int studentId = 1;
        int groupId = 2;
        studentsService.transferStudentToAnotherGroup(studentId, groupId);
        int actualGroupId = studentsService.readOneRecordFromTable(studentId).getGroup().getId();
        assertEquals(groupId, actualGroupId);
    }

    @Test
    void shouldDeleteStudentFromGroup() {
        int studentId = 1;
        studentsService.deleteStudentFromGroup(studentId);
        Student student = studentsService.readOneRecordFromTable(studentId);
        assertNull(student.getGroup());
    }

    @Test
    void shouldAssignStudentToCourse () {
        int studentId = 1;
        int courseId = 2;
        studentsService.assignStudentToCourse(studentId, courseId);
        Course course = coursesService.readOneRecordFromTable(courseId);
        List<Course> courses = studentsService.readOneRecordFromTable(studentId).getCourses();
        assertTrue(courses.contains(course));
    }

    @Test
    void shouldDeleteStudentFromCourse() {
        int studentId = 1;
        int courseId = 1;
        studentsService.deleteStudentFromCourse(studentId, courseId);
        Course course = coursesService.readOneRecordFromTable(courseId);
        List<Course> courses = studentsService.readOneRecordFromTable(studentId).getCourses();
        assertFalse(courses.contains(course));
    }

    @Test
    void shouldDeleteStudentFromAllCourses() {
        int studentId = 1;
        int courseId = 2;
        studentsService.assignStudentToCourse(studentId, courseId);
        studentsService.deleteStudentsFromAllCourses(studentId);
        List<Course> courses = studentsService.readOneRecordFromTable(studentId).getCourses();
        int actualCoursesListSize = courses.size();
        int expectedCourseListSize = 0;
        assertEquals(expectedCourseListSize, actualCoursesListSize);
    }

    @Test
    void shouldCreateStudent() {
        String nameForTest = "nameForTest";
        String surnameForTest = "surnameForTest";
        int ageForTest = 20;
        Student student = new Student();
        student.setFirstName(nameForTest);
        student.setLastName(surnameForTest);
        student.setAge(ageForTest);
        studentsService.createStudent(student);
        int actualTableSize = studentsService.readTable().size();
        int expectedTableSize = 5;
        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    void shouldReadOneRecordFromTable() {
        Student expectedStudent = firstExpectedStudent();
        int studentId = 1;
        Student actualStudent = studentsService.readOneRecordFromTable(studentId);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldReadStudentsRelatedToGroup() {
        int groupId = 1;
        List<Student> students = studentsService.readStudentsRelatedToGroup(groupId);
        List<Student> expectedStudentList = createListOfTwoStudentsForTest();
        assertEquals(expectedStudentList, students);
    }

    @Test
    void shouldReadStudentsRelatedToCourse() {
        int courseId = 1;
        List<Student> students = studentsService.readStudentsRelatedToCourse(courseId);
        List<Student> expectedStudentList = createListOfTwoStudentsForTest();
        assertEquals(expectedStudentList, students);
    }

    @Test
    void shouldReadTable() {
        List<Student> expectedStudentList = createListOfAllStudentsForTest();
        List<Student> actualStudentList = studentsService.readTable();
        assertEquals(expectedStudentList, actualStudentList);
    }

    @Test
    void shouldUpdateStudentData() {
        String nameForTest = "nameForTest";
        String surnameForTest = "surnameForTest";
        int ageForTest = 20;
        Group group = groupsService.readOneRecordFromTable(2);
        Student dataToUpdate = new Student();
        dataToUpdate.setFirstName(nameForTest);
        dataToUpdate.setLastName(surnameForTest);
        dataToUpdate.setAge(ageForTest);
        dataToUpdate.setGroup(group);

        int studentIdToUpdate = 4;
        studentsService.updateStudentData(studentIdToUpdate, dataToUpdate);

        Student updatedStudent = studentsService.readOneRecordFromTable(studentIdToUpdate);

        assertEquals(nameForTest, updatedStudent.getFirstName());
        assertEquals(surnameForTest, updatedStudent.getLastName());
        assertEquals(ageForTest, updatedStudent.getAge());
        assertEquals(group, updatedStudent.getGroup());
    }

    private Student firstExpectedStudent() {
        String student1FirstName = "Egor";
        String student1LastName = "Anchutin";
        int student1Age = 20;
        int student1GroupId = 1;
        Group student1group = groupsService.readOneRecordFromTable(student1GroupId);
        List<Course> student1courses = coursesService.readCoursesByStudentId(1);

        Student student1 = new Student(student1FirstName, student1LastName, student1Age, student1group);
        student1.setId(1);
        student1.setCourses(student1courses);

        return student1;
    }

    private Student secondExpectedStudent() {
        String student2FirstName = "Guinea";
        String student2LastName = "Pig";
        int student2Age = 21;
        int student2GroupId = 1;
        int studentId = 2;
        Group student2group = groupsService.readOneRecordFromTable(student2GroupId);
        List<Course> student2courses = coursesService.readCoursesByStudentId(studentId);

        Student student2 = new Student(student2FirstName, student2LastName, student2Age, student2group);
        student2.setId(studentId);
        student2.setCourses(student2courses);

        return student2;
    }

    private List<Student> createListOfTwoStudentsForTest() {
        List<Student> students = new ArrayList<>();
        students.add(firstExpectedStudent());
        students.add(secondExpectedStudent());
        return students;
    }

    private List<Student> createListOfAllStudentsForTest() {
        List<Student> students = new ArrayList<>();

        String student3FirstName = "Olga";
        String student3LastName = "Petrova";
        int student3Age = 23;
        int student3GroupId = 2;
        Group student3group = groupsService.readOneRecordFromTable(student3GroupId);
        List<Course> student3courses = coursesService.readCoursesByStudentId(3);
        Student student3 = new Student(student3FirstName, student3LastName, student3Age, student3group);
        student3.setId(3);
        student3.setCourses(student3courses);

        String student4FirstName = "Yaroslava";
        String student4LastName = "Igorevna";
        int student4Age = 24;
        int student4GroupId = 2;
        Group student4group = groupsService.readOneRecordFromTable(student4GroupId);
        List<Course> student4courses = coursesService.readCoursesByStudentId(4);
        Student student4 = new Student(student4FirstName, student4LastName, student4Age, student4group);
        student4.setId(4);
        student4.setCourses(student4courses);

        students.add(firstExpectedStudent());
        students.add(secondExpectedStudent());
        students.add(student3);
        students.add(student4);

        return students;
    }

}