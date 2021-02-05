package ua.com.foxminded.university.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import ua.com.foxminded.university.controllers.CoursesController;
import ua.com.foxminded.university.dao.CoursesJdbcDao;
import ua.com.foxminded.university.services.CoursesService;

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
    public CoursesService coursesService() {
        return new CoursesService(coursesJdbcDao());
    }

    @Bean
    public CoursesController coursesController() {
        return new CoursesController(coursesService());
    }
}
