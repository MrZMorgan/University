package ua.com.foxminded.university.models;

import java.util.List;
import java.util.Objects;

public class Teacher {

    private final int id;
    private final String firstName;
    private final String lastName;
    private int age;
    private List<Course> courses;

    public Teacher(int id,
                   String firstName,
                   String lastName,
                   int age,
                   List<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id &&
                age == teacher.age &&
                firstName.equals(teacher.firstName) &&
                lastName.equals(teacher.lastName) &&
                Objects.equals(courses, teacher.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, courses);
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
}
