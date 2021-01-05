package ua.com.foxminded.university;

import java.util.List;
import java.util.Objects;

public class Student {

    private final String firstName;
    private final String lasString;
    private final int age;
    private Group group;
    private List<Course> courses;

    public Student(String firstName,
                   String lasString,
                   int age, Group group,
                   List<Course> courses) {
        this.firstName = firstName;
        this.lasString = lasString;
        this.age = age;
        this.group = group;
        this.courses = courses;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLasString() {
        return lasString;
    }

    public int getAge() {
        return age;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age &&
                firstName.equals(student.firstName) &&
                lasString.equals(student.lasString) &&
                Objects.equals(group, student.group) &&
                Objects.equals(courses, student.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lasString, age, group, courses);
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lasString='" + lasString + '\'' +
                ", age=" + age +
                ", group=" + group +
                ", courses=" + courses +
                '}';
    }
}
