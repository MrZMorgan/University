package ua.com.foxminded.university.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Course {

    private final int courseId;
    private final String courseName;
    private Teacher teacher;
    private List<Group> groups;
    private List<Student> students;
}
