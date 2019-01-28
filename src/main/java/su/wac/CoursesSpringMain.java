package su.wac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
@EnableJpaRepositories
@PropertySource(value = {"classpath:env/${env}/web.properties",
        "classpath:env/${env}/db.properties"})
public class CoursesSpringMain {
    public static void main(String[] args) {
        SpringApplication.run(CoursesSpringMain.class, args);
    }
}
