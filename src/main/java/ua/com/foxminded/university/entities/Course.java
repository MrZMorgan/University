package ua.com.foxminded.university.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@ApiModel(description = "Details about course")
@Table(name = "courses")
public class Course {

    @ApiModelProperty(notes = "The unique id of the course")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ApiModelProperty(notes = "Course name")
    @Column(name = "name")
    @NotBlank(message = "Name should not be empty")
    private String name;

    @ApiModelProperty(notes = "Course teacher")
    @ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.MERGE})
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.MERGE})
    @JoinTable(name = "groups_courses",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    @ApiModelProperty(notes = "Groups who study on the course")
    private List<Group> groups;

    @ApiModelProperty(notes = "Students who study on the course")
    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.MERGE})
    @JoinTable(name = "students_courses",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;

    public Course(String name,
                  Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                ", groups=" + groups +
                ", students=" + students +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && name.equals(course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

