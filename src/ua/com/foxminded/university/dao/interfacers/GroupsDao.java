package ua.com.foxminded.university.dao.interfacers;

import ua.com.foxminded.university.exceptions.DAOException;
import ua.com.foxminded.university.models.Group;
import java.util.List;

public interface GroupsDao extends GenericDao<Group> {
    @Override
    void create(Group data);

    @Override
    Group read(int id) throws DAOException;

    @Override
    List<Group> read();

    @Override
    void update(int id, Group groupForQuery);

    @Override
    void delete(int courseId);
}
