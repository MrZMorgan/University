package ua.com.foxminded.university.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.university.entities.Group;

public interface GroupsRepository extends JpaRepository<Group, Integer> {

}
