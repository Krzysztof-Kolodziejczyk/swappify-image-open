package com.example.swappify_image_open.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "http-client.connection-properties"
)
public class HttpClientConnectorProperties extends HttpClientConfigProperties {
    public HttpClientConnectorProperties() {
    }

    public String toString() {
        return "HttpClientConnectorProperties(super=" + super.toString() + ")";
    }
}