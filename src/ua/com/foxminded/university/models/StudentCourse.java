package ua.com.foxminded.university.models;

import lombok.Data;

public @Data class StudentCourse {

    private int studentId;
    private int courseId;

    public StudentCourse(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }
}
