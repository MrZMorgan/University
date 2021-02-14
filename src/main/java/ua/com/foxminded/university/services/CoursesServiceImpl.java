package ua.com.foxminded.university.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.interfaces.CoursesRepository;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.services.interfaces.CoursesService;
import java.util.List;
import java.util.Optional;

@Service
public class CoursesServiceImpl implements CoursesService {

    private CoursesRepository coursesRepository;

    @Autowired
    public CoursesServiceImpl(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }


    @Override
    public void deleteCourseById(int courseId) {
        coursesRepository.deleteById(courseId);
    }

    @Override
    public void createCourse(Course course) {
        coursesRepository.save(course);
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
    public List<Course> readCoursesRelatedToTeacher(int teacherId) {
        return null;
    }

    @Override
    public List<Course> readCoursesByStudentId(int studentId) {
        return null;
    }

    @Override
    public void updateCourseData(int id, Course courseForQuery) {
        Course course = readOneRecordFromTable(id);
        course.setName(courseForQuery.getName());
        course.setTeacher(courseForQuery.getTeacher());
        course.setGroups(courseForQuery.getGroups());
        course.setStudents(courseForQuery.getStudents());
        coursesRepository.save(course);
    }

    @Override
    public void updateCourseId(int courseId, int updatedId) {
        Course course = readOneRecordFromTable(courseId);
        course.setId(updatedId);
        coursesRepository.save(course);
    }
}
