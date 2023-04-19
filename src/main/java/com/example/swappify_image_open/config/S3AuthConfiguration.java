package com.example.swappify_image_open.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

@Configuration
public class S3AuthConfiguration {
    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Bean
    AwsBasicCredentials createBasicCreds(){
        return AwsBasicCredentials.create(accessKey, secretKey);
    }
}
