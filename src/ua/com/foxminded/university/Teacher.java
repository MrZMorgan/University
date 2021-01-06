package ua.com.foxminded.university;

import lombok.Data;

import java.util.List;

public @Data class Teacher {

    private final String firstName;
    private final String lastName;
    private int age;
    private List<Course> courses;

    public Teacher(String firstName,
                   String lastName,
                   int age,
                   List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.courses = courses;
    }
}
