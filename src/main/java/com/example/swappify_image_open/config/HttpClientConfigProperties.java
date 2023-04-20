package com.example.swappify_image_open.config;

import lombok.Data;

@Data
public class HttpClientConfigProperties {

    int connectionPoolSize = 5;
    int connectionTTL = 9000;
    int connectionValidateAfterInactivity = 5000;
    int connectionTimeout = 20000;
    int connectionReadTimeout = 12000;
    int connectionRequestTimeout = 20000;
    int defaultKeepAliveDuration = -1;
    int connectionMaxIdleTime = 30000;

    public String toString() {
        int var10000 = this.getConnectionPoolSize();
        return "HttpClientConfigProperties(connectionPoolSize=" + var10000 + ", connectionTTL=" + this.getConnectionTTL() + ", connectionValidateAfterInactivity=" + this.getConnectionValidateAfterInactivity() + ", connectionTimeout=" + this.getConnectionTimeout() + ", connectionReadTimeout=" + this.getConnectionReadTimeout() + ", connectionRequestTimeout=" + this.getConnectionRequestTimeout() + ", defaultKeepAliveDuration=" + this.getDefaultKeepAliveDuration() + ", connectionMaxIdleTime=" + this.getConnectionMaxIdleTime() + ")";
    }
}
