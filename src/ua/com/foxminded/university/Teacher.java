package ua.com.foxminded.university;

import java.util.List;
import java.util.Objects;

public class Teacher {
    private final String firstName;
    private final String lastName;
    private final int age;
    private List<Course> courses;

    public Teacher(String firstName, String lastName, int age, List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.courses = courses;
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
        return age == teacher.age &&
                Objects.equals(firstName, teacher.firstName) &&
                Objects.equals(lastName, teacher.lastName) &&
                Objects.equals(courses, teacher.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", courses=" + courses +
                '}';
    }
}
