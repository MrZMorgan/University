package ua.com.foxminded.university.models;

import lombok.Data;
import java.util.List;

@Data
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
}
