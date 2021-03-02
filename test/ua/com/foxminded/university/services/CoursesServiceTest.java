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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {Application.class, H2JpaConfig.class})
@ActiveProfiles("test")
@Transactional
class CoursesServiceTest {

    private final static String COURSES_NAME_FOR_TEST = "nameForTest";

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private TeacherService teacherService;

    @Test
    void shouldCreateCourse() {
        String coursesNameForTest = "nameForTest";
        int expectedTableSize = 3;
        coursesService.createCourse(new Course(coursesNameForTest, null));
        assertEquals(expectedTableSize, coursesService.readTable().size());
    }

    @Test
    void shouldDeleteCourseById() {
        int expectedCourseSize = 1;
        int courseIdToDelete = 1;
        coursesService.deleteCourseById(courseIdToDelete);
        int actualCourseSize = coursesService.readTable().size();
        assertEquals(expectedCourseSize, actualCourseSize);
    }

    @Test
    void shouldRenameCourse() {
        int courseIdToRename = 1;
        coursesService.renameCourse(courseIdToRename, COURSES_NAME_FOR_TEST);
        String actualCourseName = coursesService.readOneRecordFromTable(courseIdToRename).getName();
        assertEquals(COURSES_NAME_FOR_TEST, actualCourseName);
    }

    @Test
    void shouldReadOneRecordFromTable() {
        Course course = coursesService.readOneRecordFromTable(1);
        Course expectedCourse = expectedFirstCourse();
        assertEquals(expectedCourse, course);
    }

    @Test
    void shouldReadTable() {
        List<Course> actualCourses = coursesService.readTable();
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(expectedFirstCourse());
        expectedCourses.add(expectedSecondCourse());
        assertEquals(expectedCourses, actualCourses);
    }

    @Test
    void shouldReadCoursesRelatedToTeacher() {
        int teacherId = 1;
        List<Course> actualCourses = coursesService.readCoursesRelatedToTeacher(teacherId);
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(expectedFirstCourse());
        assertEquals(expectedCourses, actualCourses);
    }

    @Test
    void shouldReadCoursesByStudentId() {
        int studentId = 1;
        List<Course> actualCourses = coursesService.readCoursesByStudentId(studentId);
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(expectedFirstCourse());
        assertEquals(expectedCourses, actualCourses);
    }

    @Test
    void shouldUpdateCourseData() {
        Course newCourseData = new Course();
        int teacherId = 2;
        Teacher teacherForUpdate = teacherService.readOneRecordFromTable(teacherId);
        newCourseData.setName(COURSES_NAME_FOR_TEST);
        newCourseData.setTeacher(teacherForUpdate);

        int courseIdToUpdate = 1;

        coursesService.updateCourseData(courseIdToUpdate, newCourseData);

        String updatedName = coursesService.readOneRecordFromTable(courseIdToUpdate).getName();
        Teacher updatedTeacher = coursesService.readOneRecordFromTable(courseIdToUpdate).getTeacher();

        assertEquals(COURSES_NAME_FOR_TEST, updatedName);
        assertEquals(teacherForUpdate, updatedTeacher);
    }

    Course expectedFirstCourse() {
        Teacher teacherForTest = teacherService.readOneRecordFromTable(1);

        Student student1ForTest = studentsService.readOneRecordFromTable(1);
        Student student2ForTest = studentsService.readOneRecordFromTable(2);
        List<Student> studentsForTest = new ArrayList<>();
        studentsForTest.add(student1ForTest);
        studentsForTest.add(student2ForTest);

        Group group = groupsService.readOneRecordFromTable(1);
        List<Group> groupsForTest = new ArrayList<>();
        groupsForTest.add(group);

        Course expectedCourse = new Course("math", teacherForTest);
        expectedCourse.setId(1);
        expectedCourse.setStudents(studentsForTest);
        expectedCourse.setGroups(groupsForTest);

        return expectedCourse;
    }

    Course expectedSecondCourse() {
        Teacher teacherForTest = teacherService.readOneRecordFromTable(2);

        Student student1ForTest = studentsService.readOneRecordFromTable(3);
        Student student2ForTest = studentsService.readOneRecordFromTable(4);
        List<Student> studentsForTest = new ArrayList<>();
        studentsForTest.add(student1ForTest);
        studentsForTest.add(student2ForTest);

        Group group = groupsService.readOneRecordFromTable(2);
        List<Group> groupsForTest = new ArrayList<>();
        groupsForTest.add(group);

        Course expectedCourse = new Course("economy", teacherForTest);
        expectedCourse.setId(2);
        expectedCourse.setStudents(studentsForTest);
        expectedCourse.setGroups(groupsForTest);

        return expectedCourse;
    }
}