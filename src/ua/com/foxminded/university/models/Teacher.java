package ua.com.foxminded.university.models;

import java.util.List;

public class Teacher {

    private final int id;
    private final String firstName;
    private final String lastName;
    private int age;
    private List<Course> courses;

    public Teacher(int id,
                   String firstName,
                   String lastName,
                   int age,
                   List<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
