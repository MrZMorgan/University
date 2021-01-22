package ua.com.foxminded.university.models;

import java.util.Objects;

public class GroupCourse {

    private int groupId;
    private int courseId;

    public GroupCourse(int groupId, int courseId) {
        this.groupId = groupId;
        this.courseId = courseId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupCourse that = (GroupCourse) o;
        return groupId == that.groupId &&
                courseId == that.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, courseId);
    }

    @Override
    public String toString() {
        return "GroupCourse{" +
                "groupId=" + groupId +
                ", courseId=" + courseId +
                '}';
    }
}
