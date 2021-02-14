package ua.com.foxminded.university.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.university.entities.Group;

public interface GroupsRepository extends JpaRepository<Group, Integer> {

}
