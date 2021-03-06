package ua.com.foxminded.university.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CoursesRepository;
import ua.com.foxminded.university.dao.StudentsRepository;
import ua.com.foxminded.university.dao.TeachersRepository;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.services.interfaces.CoursesService;
import ua.com.foxminded.university.services.interfaces.StudentsService;

import java.util.List;
import java.util.Optional;

@Service
public class CoursesServiceImpl implements CoursesService {

    private CoursesRepository coursesRepository;
    private TeachersRepository teachersRepository;
    private StudentsRepository studentsRepository;

    @Autowired
    public CoursesServiceImpl(CoursesRepository coursesRepository,
                              TeachersRepository teachersRepository,
                              StudentsRepository studentsRepository) {
        this.coursesRepository = coursesRepository;
        this.teachersRepository = teachersRepository;
        this.studentsRepository = studentsRepository;
    }

    @Override
    public void deleteCourseById(int courseId) {
        coursesRepository.deleteById(courseId);
    }

    @Override
    public Course createCourse(Course course) {
        coursesRepository.save(course);
        return course;
    }

    @Override
    public void renameCourse(int courseIdToRename, String newCourseName) {
        Course course = readOneRecordFromTable(courseIdToRename);
        course.setName(newCourseName);
        coursesRepository.save(course);
    }

    @Override
    @SneakyThrows
    public Course readOneRecordFromTable(int courseId) {
        Course course;
        Optional<Course> optional = coursesRepository.findById(courseId);
        if (optional.isPresent()) {
            course = optional.get();
        } else {
            throw new DAOException(courseId);
        }
        return course;
    }

    @Override
    public List<Course> readTable() {
        return coursesRepository.findAll();
    }

    @Override
    @SneakyThrows
    public List<Course> readCoursesRelatedToTeacher(int teacherId) {
        Teacher teacher;
        Optional<Teacher> optional = teachersRepository.findById(teacherId);
        if (optional.isPresent()) {
            teacher = optional.get();
        } else {
            throw new DAOException(teacherId);
        }
        List<Course> courses = teacher.getCourses();
        return courses;
    }

    @Override
    @SneakyThrows
    public List<Course> readCoursesByStudentId(int studentId) {
        Student student;
        Optional<Student> optional = studentsRepository.findById(studentId);
        if (optional.isPresent()) {
            student = optional.get();
        } else {
            throw new DAOException(studentId);
        }
        List<Course> courses = student.getCourses();
        return courses;
    }

    @Override
    public Course updateCourseData(int id, Course courseForQuery) {
        Course course = readOneRecordFromTable(id);
        course.setName(courseForQuery.getName());
        course.setTeacher(courseForQuery.getTeacher());
        course.setGroups(courseForQuery.getGroups());
        course.setStudents(courseForQuery.getStudents());
        coursesRepository.save(course);

        return course;
    }
}
