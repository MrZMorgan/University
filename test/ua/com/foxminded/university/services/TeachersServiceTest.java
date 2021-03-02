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
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.services.interfaces.CoursesService;
import ua.com.foxminded.university.services.interfaces.GroupsService;
import ua.com.foxminded.university.services.interfaces.StudentsService;
import ua.com.foxminded.university.services.interfaces.TeacherService;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {Application.class, H2JpaConfig.class})
@ActiveProfiles("test")
@Transactional
public class TeachersServiceTest {

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private TeacherService teacherService;

    @Test
    public void shouldCreateTeacher() {
        String nameForTest = "nameForTest";
        String surnameForTest = "surnameForTest";
        int ageForTest = 20;
        Teacher teacher = new Teacher();
        teacher.setFirstName(nameForTest);
        teacher.setLastName(surnameForTest);
        teacher.setAge(ageForTest);

        teacherService.createTeacher(teacher);
        int expectedTableSize = 3;
        int actualTableSize = teacherService.readTable().size();

        assertEquals(expectedTableSize, actualTableSize);
    }

    @Test
    public void shouldDeleteTeacherById() {
        teacherService.deleteTeacherById(1);
        int expectedStudentsSize = 1;
        int actualStudentsSize = teacherService.readTable().size();
        assertEquals(expectedStudentsSize, actualStudentsSize);
    }

    @Test
    public void shouldDeleteTeacherFromCourse() {
        int courseId = 1;
        teacherService.deleteTeacherFromCourse(courseId);
        Course course = coursesService.readOneRecordFromTable(courseId);
        assertNull(course.getTeacher());
    }

    @Test
    public void shouldAssignTeacherToCourse() {
        int teacherId = 1;
        int courseId = 2;
        teacherService.assignTeacherToCourse(teacherId, courseId);
        List<Course> courses = teacherService.readOneRecordFromTable(teacherId).getCourses();
        Course course = coursesService.readOneRecordFromTable(courseId);
        assertTrue(courses.contains(course));
    }

    @Test
    public void shouldDeleteTeacherFromAllCourses() {
        int teacherId = 1;
        int courseId = 2;
        teacherService.assignTeacherToCourse(teacherId, courseId);
        teacherService.deleteTeacherFromAllCourses(1);
        int expectedCourseTableSize = 0;
        int actualCoursesTableSize = teacherService.readOneRecordFromTable(teacherId).getCourses().size();
        assertEquals(expectedCourseTableSize, actualCoursesTableSize);
    }

    @Test
    public void shouldReadOneRecordFromTable() {
        Teacher expectedTeacher = firstExpectedTeacher();
        int teacherId = 1;
        Teacher actualTeacher = teacherService.readOneRecordFromTable(teacherId);
        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    public void shouldReadTable() {
        List<Teacher> expectedTeacherList = createTeachersListForTest();
        List<Teacher> actualTeacherList = teacherService.readTable();
        assertEquals(expectedTeacherList, actualTeacherList);
    }

    @Test
    public void shouldUpdateTeacherData() {
        String nameForTest = "nameForTest";
        String surnameForTest = "surnameForTest";
        int ageForTest = 20;
        Teacher dataToUpdate = new Teacher();
        dataToUpdate.setFirstName(nameForTest);
        dataToUpdate.setLastName(surnameForTest);
        dataToUpdate.setAge(ageForTest);

        int teacherIdToUpdate = 1;
        teacherService.updateTeacherData(teacherIdToUpdate, dataToUpdate);

        Teacher updatedTeacher = teacherService.readOneRecordFromTable(teacherIdToUpdate);

        assertEquals(nameForTest, updatedTeacher.getFirstName());
        assertEquals(surnameForTest, updatedTeacher.getLastName());
        assertEquals(ageForTest, updatedTeacher.getAge());
    }

    private Teacher firstExpectedTeacher() {
        int teacherId = 1;
        String teacherFirstName = "Ivan";
        String teacher1LastName = "Smirnov";
        int teacherAge = 20;
        List<Course> teacherCourseList = coursesService.readCoursesRelatedToTeacher(teacherId);
        Teacher teacher = new Teacher(teacherFirstName, teacher1LastName, teacherAge);
        teacher.setId(teacherId);
        teacher.setCourses(teacherCourseList);

        return teacher;
    }

    private List<Teacher> createTeachersListForTest() {
        List<Teacher> teachers = new ArrayList<>();

        Teacher teacher1 = firstExpectedTeacher();

        int teacher2Id = 2;
        String teacher2FirstName = "Oleg";
        String teacher2LastName = "Sidorov";
        int teacher2Age = 25;
        List<Course> teacher2CourseList = coursesService.readCoursesRelatedToTeacher(teacher2Id);
        Teacher teacher2 = new Teacher(teacher2FirstName, teacher2LastName, teacher2Age);
        teacher2.setId(2);
        teacher2.setCourses(teacher2CourseList);

        teachers.add(teacher1);
        teachers.add(teacher2);

        return teachers;
    }
}
