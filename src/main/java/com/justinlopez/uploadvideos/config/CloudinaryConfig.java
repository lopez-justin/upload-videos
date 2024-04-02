package com.justinlopez.uploadvideos.config;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
@PropertySource("classpath:cloudinary.properties")
public class CloudinaryConfig {

    private final Environment env;
    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> values = Map.of(
                "cloud_name", env.getProperty("cloudinary.cloud_name"),
                "api_key", env.getProperty("cloudinary.api_key"),
                "api_secret", env.getProperty("cloudinary.api_secret")
        );
        return new Cloudinary(values);
    }

}
