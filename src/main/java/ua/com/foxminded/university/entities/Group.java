package ua.com.foxminded.university.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@ApiModel(description = "Details about group")
@Table(name = "groups")
public class Group {

    @ApiModelProperty(notes = "The unique id of the group")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ApiModelProperty(notes = "Group name")
    @Column(name = "name")
    @NotBlank(message = "Name should not be empty")
    private String name;

    @ApiModelProperty(notes = "Group students")
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "group")
    private List<Student> students;

    @ApiModelProperty(notes = "Group courses")
    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.MERGE})
    @JoinTable(name = "groups_courses",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;

    public Group(String name) {
        this.name = name;
    }
}