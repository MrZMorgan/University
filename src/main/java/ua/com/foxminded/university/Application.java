package ua.com.foxminded.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class Application {

    public static final String TITLE = "University database API";
    public static final String DESCRIPTION = "Spring boot project";
    public static final String VERSION = "1.0";
    public static final String TERMS_OF_SERVICE_URL = "Free to use";
    public static final String NAME = "Egor Anchutin";
    public static final String URL = "https://foxminded.com.ua/";
    public static final String EMAIL = "zbit16@gmail.com";
    public static final String LICENSE = "API License";
    public static final String LICENSE_URL = "https://foxminded.com.ua/";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ua.com.foxminded.university.rest"))
                .build()
                .apiInfo(appDetails());
    }

    private ApiInfo appDetails() {
        return new ApiInfo(
                TITLE,
                DESCRIPTION,
                VERSION,
                TERMS_OF_SERVICE_URL,
                new Contact(NAME, URL, EMAIL),
                LICENSE,
                LICENSE_URL,
                Collections.emptyList());
    }
}
