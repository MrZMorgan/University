package ua.com.foxminded.university.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private List<Course> courses;
}
