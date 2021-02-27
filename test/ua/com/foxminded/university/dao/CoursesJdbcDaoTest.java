package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.foxminded.university.entities.Course;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)

class CoursesJdbcDaoTest {

    private final static String COURSE_NAME_FOR_TEST = "chemistry";

    @Test
    @Transactional
    void shouldCreateCourse() {

    }

    @Test
    @Transactional
    void shouldReadCourse() {

    }

    @Test
    @Transactional
    void shouldReadAllCourses() {

    }

    @Test
    @Transactional
    void shouldUpdateCourse() {

    }

    @Test
    @Transactional
    void shouldDeleteCourse() {

    }

    Course expectedFirstGroup() {
        return null;
    }

    Course expectedSecondGroup() {
        return null;
    }
}