package ua.com.foxminded.university.models;

import lombok.Data;
import java.util.List;

@Data
public class Group {

    private final int groupId;
    private String groupName;
    private List<Student> students;

    public Group(int groupId,
                 String groupName,
                 List<Student> students) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.students = students;
    }
}