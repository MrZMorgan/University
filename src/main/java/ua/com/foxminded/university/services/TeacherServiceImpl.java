package ua.com.foxminded.university.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CoursesRepository;
import ua.com.foxminded.university.dao.TeachersRepository;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.services.interfaces.CoursesService;
import ua.com.foxminded.university.services.interfaces.TeacherService;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private TeachersRepository teachersRepository;
    private CoursesRepository coursesRepository;

    @Autowired
    public TeacherServiceImpl(TeachersRepository teachersRepository,
                              CoursesRepository coursesRepository) {
        this.teachersRepository = teachersRepository;
        this.coursesRepository = coursesRepository;
    }

    @Override
    public void createTeacher(Teacher teacher) {
        teachersRepository.save(teacher);
    }

    @Override
    public void deleteTeacherById(int teacherId) {
        teachersRepository.deleteById(teacherId);
    }

    @Override
    public void deleteTeacherFromCourse(int courseId) {
        Course course = readOneCourse(courseId);
        Teacher teacher = course.getTeacher();
        teacher.getCourses().remove(course);
        teachersRepository.save(teacher);
    }

    @Override
    public void assignTeacherToCourse(int teacherId, int courseId) {
        Course course = readOneCourse(courseId);
        Teacher teacher = readOneRecordFromTable(teacherId);
        List<Course> courses = teacher.getCourses();
        courses.add(course);
        teachersRepository.save(teacher);
    }

    @Override
    public void deleteTeacherFromAllCourses(int teacherId) {
        Teacher teacher = readOneRecordFromTable(teacherId);
        List<Course> courses = teacher.getCourses();
        courses.clear();
        teachersRepository.save(teacher);
    }

    @Override
    @SneakyThrows
    public Teacher readOneRecordFromTable(int teacherId) {
        Teacher teacher;
        Optional<Teacher> optional = teachersRepository.findById(teacherId);
        if (optional.isPresent()) {
            teacher = optional.get();
        } else {
            throw new DAOException(teacherId);
        }
        return teacher;
    }

    @Override
    public List<Teacher> readTable() {
        return teachersRepository.findAll();
    }

    @Override
    public void updateTeacherData(int id, Teacher teacherForQuery) {
        Teacher teacher = readOneRecordFromTable(id);
        teacher.setAge(teacherForQuery.getAge());
        teacher.setFirstName(teacherForQuery.getFirstName());
        teacher.setLastName(teacherForQuery.getLastName());
        teacher.setCourses(teacherForQuery.getCourses());
        teachersRepository.save(teacher);
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
