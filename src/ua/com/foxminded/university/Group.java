package ua.com.foxminded.university;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupId == group.groupId &&
                groupName.equals(group.groupName) &&
                Objects.equals(students, group.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName, students);
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", students=" + students +
                '}';
    }
}
