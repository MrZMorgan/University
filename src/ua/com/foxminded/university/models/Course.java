package ua.com.foxminded.university.models;

import lombok.Data;

import java.util.List;

@Data
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
}
