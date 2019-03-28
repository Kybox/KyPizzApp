package fr.kybox.kypizzapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "fr.kybox.kypizzapp.repository")
public class KypizzappApplication {

    public static void main(String[] args) {
        SpringApplication.run(KypizzappApplication.class, args);
    }

}
