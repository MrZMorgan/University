package ua.com.foxminded.university.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Teacher {

    private final int id;
    private final String firstName;
    private final String lastName;
    private int age;
    private List<Course> courses;
}
