package ua.com.foxminded.university.services.interfaces;

import ua.com.foxminded.university.entities.Course;
import java.util.List;

public interface CoursesService {

    public void deleteCourseById(int courseId);

    public Course createCourse(Course course);

    public void renameCourse(int courseIdToRename, String newCourseName);

    public Course readOneRecordFromTable(int courseId);

    public List<Course> readTable();

    public List<Course> readCoursesRelatedToTeacher(int teacherId);

    public List<Course> readCoursesByStudentId(int studentId);

    public Course updateCourseData(int id, Course courseForQuery);
}
