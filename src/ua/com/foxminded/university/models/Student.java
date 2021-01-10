package ua.com.foxminded.university.models;

import lombok.Data;

import java.util.List;

public @Data class Student {

    private final String firstName;
    private final String lastName;
    private int age;
    private Group group;
    private List<Course> courses;

    public Student(String firstName, String lastName, int age, Group group, List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.group = group;
        this.courses = courses;
    }
}
