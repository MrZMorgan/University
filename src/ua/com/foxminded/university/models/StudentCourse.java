package ua.com.foxminded.university.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentCourse {

    private int studentId;
    private int courseId;
}
