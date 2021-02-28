package ua.com.foxminded.university.services.interfaces;

import ua.com.foxminded.university.entities.Student;
import java.util.List;

public interface StudentsService {

    public void deleteStudentById(int studentId);

    public void transferStudentToAnotherGroup(int studentId, int groupId);

    public void deleteStudentFromGroup(int studentId);

    public void assignStudentToCourse(int studentId, int courseId);

    public void deleteStudentFromCourse(int studentId, int courseId);

    public void deleteStudentsFromAllCourses(int studentId);

    public void createStudent(Student student);

    public Student readOneRecordFromTable(int studentId);

    public List<Student> readStudentsRelatedToGroup(int groupId);

    public List<Student> readStudentsRelatedToCourse(int courseId);

    public List<Student> readTable();

    public void updateStudentData(int id, Student studentForQuery);
}
