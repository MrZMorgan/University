package ua.com.foxminded.university.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.com.foxminded.university.controllers.CoursesController;
import ua.com.foxminded.university.controllers.GroupsController;
import ua.com.foxminded.university.controllers.StudentsController;
import ua.com.foxminded.university.controllers.TeachersController;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.dao.GroupsJdbcDao;
import ua.com.foxminded.university.dao.StudentsJdbcDao;
import ua.com.foxminded.university.dao.TeachersJdbcDao;
import ua.com.foxminded.university.services.CoursesService;
import ua.com.foxminded.university.services.GroupsService;
import ua.com.foxminded.university.services.StudentsService;
import ua.com.foxminded.university.services.TeacherService;

import javax.sql.DataSource;

@Configuration
public class TestConfig implements WebMvcConfigurer {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public CoursesJdbcDao coursesJdbcDao() {
        return new CoursesJdbcDao(jdbcTemplate());
    }

    @Bean
    public StudentsJdbcDao studentsJdbcDao() {
        return new StudentsJdbcDao(jdbcTemplate());
    }

    @Bean
    public CoursesService coursesService() {
        return new CoursesService(coursesJdbcDao());
    }

    @Bean
    public CoursesController coursesController() {
        return new CoursesController(coursesService());
    }

    @Bean
    public GroupsJdbcDao groupsJdbcDao() {
        return new GroupsJdbcDao(jdbcTemplate());
    }

    @Bean
    public GroupsService groupsService() {
        return new GroupsService(studentsJdbcDao(), groupsJdbcDao());
    }

    @Bean
    public GroupsController groupsController() {
        return new GroupsController(groupsService());
    }

    @Bean
    public StudentsService studentsService() {
        return new StudentsService(studentsJdbcDao());
    }

    @Bean
    public StudentsController studentsController() {
        return new StudentsController(studentsService());
    }

    @Bean
    public TeachersJdbcDao teachersJdbcDao() {
        return new TeachersJdbcDao(jdbcTemplate());
    }

    @Bean
    public TeacherService teacherService() {
        return new TeacherService(coursesJdbcDao(), teachersJdbcDao());
    }

    @Bean
    public TeachersController teachersController() {
        return new TeachersController(teacherService());
    }
}
