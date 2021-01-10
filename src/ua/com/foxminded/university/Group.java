package ua.com.foxminded.university;

import lombok.Data;

import java.util.List;

public @Data class Group {

    private final int groupId;
    private String groupName;

    public Group(int groupId,
                 String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }
}
