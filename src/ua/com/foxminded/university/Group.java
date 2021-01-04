package ua.com.foxminded.university;

import java.util.List;

public class Group {
    private final int groupId;
    private final String groupName;
    private List<Student> students;

    public Group(int groupId, String groupName, List<Student> students) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.students = students;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
