package ua.com.foxminded.university;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.GroupsDao;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");

        GroupsDao groupsDao = new GroupsDao(jdbcTemplate);
        groupsDao.create();
    }

}
