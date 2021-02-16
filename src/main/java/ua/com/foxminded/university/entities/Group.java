package ua.com.foxminded.university.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Name should not be empty")
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "group")
    private List<Student> students;

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

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && name.equals(group.name) && Objects.equals(students, group.students) && Objects.equals(courses, group.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, students, courses);
    }
}