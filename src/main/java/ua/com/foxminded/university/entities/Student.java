package ua.com.foxminded.university.entities;

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
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @NotBlank(message = "Firstname should not be empty")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Lastname should not be empty")
    private String lastName;

    @Column(name = "age")
    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    @ManyToOne(cascade = {CascadeType.PERSIST,
                          CascadeType.DETACH,
                          CascadeType.REFRESH,
                          CascadeType.MERGE})
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToMany(cascade = {CascadeType.PERSIST,
                           CascadeType.DETACH,
                           CascadeType.REFRESH,
                           CascadeType.MERGE})
    @JoinTable(name = "students_courses",
               joinColumns = @JoinColumn(name = "student_id"),
               inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;

    public Student(String firstName,
                   String lastName,
                   int age,
                   Group group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && age == student.age && firstName.equals(student.firstName) && lastName.equals(student.lastName) && Objects.equals(group, student.group) && Objects.equals(courses, student.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, group, courses);
    }
}
