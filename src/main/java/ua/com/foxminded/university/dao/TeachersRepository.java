package ua.com.foxminded.university.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.university.entities.Teacher;

public interface TeachersRepository extends JpaRepository<Teacher, Integer> {

}
