package ua.com.foxminded.university.models;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId == course.courseId &&
                courseName.equals(course.courseName) &&
                Objects.equals(teacher, course.teacher) &&
                Objects.equals(groups, course.groups) &&
                Objects.equals(students, course.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, teacher, groups, students);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", teacher=" + teacher +
                ", groups=" + groups +
                ", students=" + students +
                '}';
    }
}
