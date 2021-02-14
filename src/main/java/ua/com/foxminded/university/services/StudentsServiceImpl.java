package ua.com.foxminded.university.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.StudentsRepository;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.services.interfaces.CoursesService;
import ua.com.foxminded.university.services.interfaces.StudentsService;
import java.util.List;
import java.util.Optional;

@Service
public class StudentsServiceImpl implements StudentsService {

    private StudentsRepository studentsRepository;
    private GroupsServiceImpl groupsService;
    private CoursesService coursesService;

    @Autowired
    public StudentsServiceImpl(StudentsRepository studentsRepository,
                               GroupsServiceImpl groupsService,
                               CoursesService coursesService) {
        this.studentsRepository = studentsRepository;
        this.groupsService = groupsService;
        this.coursesService = coursesService;
    }

    @Override
    public void deleteStudentById(int studentId) {
        studentsRepository.deleteById(studentId);
    }

    @Override
    public void transferStudentToAnotherGroup(int studentId, int groupId) {
        Student student = readOneRecordFromTable(studentId);
        Group group = groupsService.readOneRecordFromTable(groupId);
        student.setGroup(group);
        studentsRepository.save(student);
    }

    @Override
    public void deleteStudentFromGroup(int groupId) {
        studentsRepository.deleteById(groupId);
    }

    @Override
    public void assignStudentToCourse(int studentId, int courseId) {
        Student student = readOneRecordFromTable(studentId);
        Course course = coursesService.readOneRecordFromTable(courseId);
        List<Course> courses = student.getCourses();
        courses.add(course);
        studentsRepository.save(student);
    }

    @Override
    public void deleteStudentFromCourse(int studentId, int courseId) {
        Course course = coursesService.readOneRecordFromTable(courseId);
        Student student = readOneRecordFromTable(studentId);
        List<Course> courses = student.getCourses();
        courses.remove(course);
        studentsRepository.save(student);
    }

    @Override
    public void deleteStudentsFromAllCourses(int studentId) {
        Student student = readOneRecordFromTable(studentId);
        List<Course> courses = student.getCourses();
        courses.clear();
        studentsRepository.save(student);
    }

    @Override
    public void createStudent(Student student) {
        studentsRepository.save(student);
    }

    @Override
    @SneakyThrows
    public Student readOneRecordFromTable(int studentId) {
        Student student;
        Optional<Student> optional = studentsRepository.findById(studentId);
        if (optional.isPresent()) {
            student = optional.get();
        } else {
            throw new DAOException(studentId);
        }
        return student;
    }

    @Override
    public List<Student> readStudentsRelatedToGroup(int groupId) {
        Group group = groupsService.readOneRecordFromTable(groupId);
        List<Student> students = group.getStudents();
        return students;
    }

    @Override
    public List<Student> readStudentsRelatedToCourse(int courseId) {
        Course course = coursesService.readOneRecordFromTable(courseId);
        List<Student> students = course.getStudents();
        return students;
    }

    @Override
    public List<Student> readTable() {
        return studentsRepository.findAll();
    }

    @Override
    public void updateStudentData(int id, Student studentForQuery) {
        Student student = readOneRecordFromTable(id);
        student.setAge(studentForQuery.getAge());
        student.setFirstName(studentForQuery.getFirstName());
        student.setLastName(studentForQuery.getLastName());
        student.setGroup(studentForQuery.getGroup());
        student.setCourses(studentForQuery.getCourses());
        studentsRepository.save(student);
    }

    @Override
    public void updateStudentId(int studentId, int updatedId) {
        Student student = readOneRecordFromTable(studentId);
        student.setId(studentId);
        studentsRepository.save(student);
    }
}
