package fr.kybox.kypizzapp;

import fr.kybox.kypizzapp.security.jwt.property.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
@EnableMongoRepositories(basePackages = "fr.kybox.kypizzapp.repository")
public class KypizzappApplication {

    public static void main(String[] args) {
        SpringApplication.run(KypizzappApplication.class, args);
    }

}
