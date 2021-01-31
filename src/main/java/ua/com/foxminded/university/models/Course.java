package ua.com.foxminded.university.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private int courseId;
    private String courseName;
    private Teacher teacher;
    private List<Group> groups;
    private List<Student> students;
}
