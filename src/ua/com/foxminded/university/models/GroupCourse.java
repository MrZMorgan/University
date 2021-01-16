package ua.com.foxminded.university.models;

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
}
