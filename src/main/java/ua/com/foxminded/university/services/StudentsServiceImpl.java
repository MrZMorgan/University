package ua.com.foxminded.university.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CoursesRepository;
import ua.com.foxminded.university.dao.GroupsRepository;
import ua.com.foxminded.university.dao.StudentsRepository;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.services.interfaces.CoursesService;
import ua.com.foxminded.university.services.interfaces.GroupsService;
import ua.com.foxminded.university.services.interfaces.StudentsService;
import java.util.List;
import java.util.Optional;

@Service
public class StudentsServiceImpl implements StudentsService {

    private StudentsRepository studentsRepository;
    private GroupsRepository groupsRepository;
    private CoursesRepository coursesRepository;


    public StudentsServiceImpl(StudentsRepository studentsRepository,
                               GroupsRepository groupsRepository,
                               CoursesRepository coursesRepository) {
        this.studentsRepository = studentsRepository;
        this.groupsRepository = groupsRepository;
        this.coursesRepository = coursesRepository;
    }

    @Override
    public void deleteStudentById(int studentId) {
        studentsRepository.deleteById(studentId);
    }

    @Override
    public void transferStudentToAnotherGroup(int studentId, int groupId) {
        Student student = readOneRecordFromTable(studentId);
        Group group = readOneGroup(groupId);
        student.setGroup(group);
        studentsRepository.save(student);
    }

    @Override
    public void deleteStudentFromGroup(int studentId) {
        Student student = readOneRecordFromTable(studentId);
        student.setGroup(null);
        studentsRepository.save(student);
    }

    @Override
    public void assignStudentToCourse(int studentId, int courseId) {
        Student student = readOneRecordFromTable(studentId);
        Course course = readOneCourse(courseId);
        List<Course> courses = student.getCourses();
        courses.add(course);
        studentsRepository.save(student);
    }

    @Override
    public void deleteStudentFromCourse(int studentId, int courseId) {
        Course course = readOneCourse(courseId);
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
        Group group = readOneGroup(groupId);
        List<Student> students = group.getStudents();
        return students;
    }

    @Override
    public List<Student> readStudentsRelatedToCourse(int courseId) {
        Course course = readOneCourse(courseId);
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

    @SneakyThrows
    public Group readOneGroup(int groupId) {
        Group group;
        Optional<Group> optional = groupsRepository.findById(groupId);
        if (optional.isPresent()) {
            group = optional.get();
        } else {
            throw new DAOException(groupId);
        }
        return group;
    }

    @SneakyThrows
    public Course readOneCourse(int courseId) {
        Course course;
        Optional<Course> optional = coursesRepository.findById(courseId);
        if (optional.isPresent()) {
            course = optional.get();
        } else {
            throw new DAOException(courseId);
        }
        return course;
    }

}
