package ua.com.foxminded.university;

import java.util.List;

public class Teacher {
    private final String firstName;
    private final String lastName;
    private final int age;
    private List<Course> courses;

    public Teacher(String firstName, String lastName, int age, List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.courses = courses;
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
