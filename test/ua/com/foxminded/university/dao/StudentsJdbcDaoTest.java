package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.foxminded.university.entities.Student;

import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
class StudentsJdbcDaoTest {

    @Test
    @Transactional
    void shouldCreateStudent() {

    }

    @Test
    @Transactional
    void shouldReadStudent() {

    }

    @Test
    @Transactional
    void shouldReadAllStudents() {

    }

    @Test
    @Transactional
    void shouldUpdateStudent() {

    }

    @Test
    @Transactional
    void shouldDeleteStudent() {

    }

    private Student firstExpectedStudent() {
        return null;
    }

    private List<Student> createStudentsListForTest() {
        return null;
    }
}