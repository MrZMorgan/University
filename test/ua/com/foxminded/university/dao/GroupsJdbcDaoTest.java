package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.services.interfaces.GroupsService;

import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class GroupsJdbcDaoTest {

    private final static String NAME_FOR_TEST = "GS-10-1";
    private final static String NAME_FOR_TEST_2 = "ERB-11-2";

    @Autowired
    private GroupsService groupsService;

    @Test
    @Transactional
    void shouldCreateGroup() {

    }

    @Test
    @Transactional
    void shouldReadGroup() {

    }

    @Test
    @Transactional
    void shouldReadAllGroups() {
        List<Group> groups = groupsService.readTable();


    }

    @Test
    @Transactional
    void shouldUpdateGroup() {

    }

    @Test
    @Transactional
    void shouldDeleteGroup() {

    }

    Group expectedFirstGroup() {

        return null;
    }

    Group expectedSecondGroup() {

        return null;
    }
}