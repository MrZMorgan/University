package ua.com.foxminded.university.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher {

    @ApiModelProperty(notes = "The unique id of the teacher")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ApiModelProperty(notes = "Teacher firstname")
    @Column(name = "first_name")
    @NotBlank(message = "Lastname should not be empty")
    private String firstName;

    @ApiModelProperty(notes = "Teacher lastname")
    @Column(name = "last_name")
    @NotBlank(message = "Lastname should not be empty")
    private String lastName;

    @ApiModelProperty(notes = "Teacher age")
    @Column(name = "age")
    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    @ApiModelProperty(notes = "Courses taught by the teacher")
    @OneToMany(cascade = {CascadeType.ALL},
            mappedBy = "teacher")
    private List<Course> courses;

    public Teacher(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
