package com.example.gaff.api_user.map;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "com.gaff.google-maps")
public class GoogleMapsClientProperties {

    private String token;
}
