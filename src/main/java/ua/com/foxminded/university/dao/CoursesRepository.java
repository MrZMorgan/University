package ua.com.foxminded.university.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.university.entities.Course;

public interface CoursesRepository extends JpaRepository<Course, Integer> {
    
}
