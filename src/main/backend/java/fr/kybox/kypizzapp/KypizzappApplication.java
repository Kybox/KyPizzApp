package fr.kybox.kypizzapp;

import fr.kybox.kypizzapp.config.property.CookieProperties;
import fr.kybox.kypizzapp.config.property.JwtProperties;
import fr.kybox.kypizzapp.config.property.RestaurantProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableConfigurationProperties({JwtProperties.class, CookieProperties.class, RestaurantProperties.class})
//@EnableMongoRepositories(basePackages = "fr.kybox.kypizzapp.repository")
public class KypizzappApplication {

    public static void main(String[] args) {
        SpringApplication.run(KypizzappApplication.class, args);
    }

}
