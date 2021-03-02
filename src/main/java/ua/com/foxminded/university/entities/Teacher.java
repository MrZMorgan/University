package ua.com.foxminded.university.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

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
            mappedBy = "teacher", fetch = FetchType.EAGER)
    private List<Course> courses;

    public Teacher(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", courses=" + courses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id && age == teacher.age && firstName.equals(teacher.firstName) && lastName.equals(teacher.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age);
    }
}
