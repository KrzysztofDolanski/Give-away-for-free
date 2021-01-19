package com.example.gaff;

import com.example.gaff.api_user.map.GoogleMapsClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
//        (exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
@EnableConfigurationProperties(GoogleMapsClientProperties.class)

public class GaffApplication {

    public static void main(String[] args) {
        SpringApplication.run(GaffApplication.class, args);
    }

}
