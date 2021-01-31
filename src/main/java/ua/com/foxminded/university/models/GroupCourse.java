package ua.com.foxminded.university.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupCourse {

    private int groupId;
    private int courseId;
}
