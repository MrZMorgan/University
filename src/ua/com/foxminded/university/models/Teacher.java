package ua.com.foxminded.university.models;

import lombok.Data;

import java.util.List;

@Data
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
}
