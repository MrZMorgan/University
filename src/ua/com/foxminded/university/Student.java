package ua.com.foxminded.university;

import java.util.List;

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
}
