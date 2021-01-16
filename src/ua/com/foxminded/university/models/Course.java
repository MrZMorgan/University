package ua.com.foxminded.university.models;

import java.util.List;

public class Course {

    private final int courseId;
    private final String courseName;
    private Teacher teacher;
    private List<Group> groups;
    private List<Student> students;

    public Course(int courseId,
                  String courseName,
                  Teacher teacher,
                  List<Group> groups,
                  List<Student> students) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacher = teacher;
        this.groups = groups;
        this.students = students;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
