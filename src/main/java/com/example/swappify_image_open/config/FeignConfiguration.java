package com.example.swappify_image_open.config;

import com.example.swappify_image_open.factory.HttpClientFactory;
import org.apache.http.client.HttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.example.swappifyauthconnector.connector"})
@EnableConfigurationProperties(HttpClientConnectorProperties.class)
public class FeignConfiguration {

    @Bean
    @ConditionalOnClass({HttpClient.class})
    public HttpClient httpClient(HttpClientConnectorProperties properties){
        return new HttpClientFactory(properties).createHttpClient();
    }
}
