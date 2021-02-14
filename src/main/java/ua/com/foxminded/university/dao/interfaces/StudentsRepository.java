package ua.com.foxminded.university.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.university.entities.Student;

public interface StudentsRepository extends JpaRepository<Student, Integer> {

}
