package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.foxminded.university.entities.Teacher;

import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
class TeachersJdbcDaoTest {

    @Test
    @Transactional
    void shouldCreateTeacher() {

    }

    @Test
    @Transactional
    void shouldReadTeacher() {

    }

    @Test
    @Transactional
    void shouldReadAllTeachers() {

    }

    @Test
    @Transactional
    void shouldUpdateTeacher() {

    }

    @Test
    @Transactional
    void shouldDeleteTeachers() {

    }

    private Teacher firstExpectedTeacher() {
        return null;
    }

    private List<Teacher> createTeachersListForTest() {
        return null;
    }
}