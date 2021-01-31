package ua.com.foxminded.university.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Group {

    private final int id;
    private String name;
    private List<Student> students;
}