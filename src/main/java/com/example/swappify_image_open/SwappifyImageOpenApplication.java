package com.example.swappify_image_open;

import com.example.swappify_image_open.config.HttpClientConnectorProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableConfigurationProperties(HttpClientConnectorProperties.class)
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class SwappifyImageOpenApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwappifyImageOpenApplication.class, args);
    }
}
