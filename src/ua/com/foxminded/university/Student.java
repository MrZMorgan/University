package ua.com.foxminded.university;

import lombok.Data;

import java.util.List;

public @Data class Student {

    private final String firstName;
    private final String lasString;
    private int age;
    private Group group;
    private List<Course> courses;

    public Student(String firstName,
                   String lasString,
                   int age,
                   Group group,
                   List<Course> courses) {
        this.firstName = firstName;
        this.lasString = lasString;
        this.age = age;
        this.group = group;
        this.courses = courses;
    }
}
