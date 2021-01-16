package ua.com.foxminded.university.models;

import java.util.List;

public class Student {

    private final int id;
    private final String firstName;
    private final String lastName;
    private int age;
    private Group group;
    private List<Course> courses;

    public Student(int id,
                   String firstName,
                   String lastName,
                   int age,
                   Group group,
                   List<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.group = group;
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
