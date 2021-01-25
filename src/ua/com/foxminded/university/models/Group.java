package ua.com.foxminded.university.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Group {

    private final int groupId;
    private String groupName;
    private List<Student> students;
}