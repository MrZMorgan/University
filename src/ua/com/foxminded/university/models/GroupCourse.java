package ua.com.foxminded.university.models;

import lombok.Data;

public @Data class GroupCourse {
    private int groupId;
    private int courseId;

    public GroupCourse(int groupId, int courseId) {
        this.groupId = groupId;
        this.courseId = courseId;
    }
}
