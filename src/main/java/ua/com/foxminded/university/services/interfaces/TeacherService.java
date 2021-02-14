package ua.com.foxminded.university.services.interfaces;

import ua.com.foxminded.university.entities.Teacher;
import java.util.List;

public interface TeacherService {

    public void createTeacher(Teacher teacher);

    public void deleteTeacherById(int teacherId);

    public void deleteTeacherFromCourse(int courseId);

    public void assignTeacherToCourse(int teacherId, int courseId);

    public void deleteTeacherFromAllCourses(int teacherId);

    public Teacher readOneRecordFromTable(int teacherId);

    public List<Teacher> readTable();
    public void updateTeacherData(int id, Teacher teacherForQuery);
}
