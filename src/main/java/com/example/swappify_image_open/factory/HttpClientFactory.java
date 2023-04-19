package com.example.swappify_image_open.factory;

import java.util.concurrent.TimeUnit;

import com.example.swappify_image_open.config.HttpClientConfigProperties;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientFactory {
    private static final Logger log = LoggerFactory.getLogger(HttpClientFactory.class);
    private final HttpClientConfigProperties properties;

    public HttpClientFactory(HttpClientConfigProperties properties) {
        this.properties = properties;
    }

    public HttpClient createHttpClient() {
        return this.createHttpClient((HttpClientBuilder)null);
    }

    public HttpClient createHttpClient(HttpClientBuilder builder) {
        if (builder == null) {
            builder = HttpClientBuilder.create();
        }

        RequestConfig config = this.createRequestConfig();
        return builder.setConnectionManager(this.createConnectionManager(this.properties.getConnectionPoolSize(), this.properties.getConnectionTTL(), this.properties.getConnectionValidateAfterInactivity(), this.properties.getConnectionReadTimeout())).setDefaultRequestConfig(config).setKeepAliveStrategy(this.createKeepAliveStrategy()).evictExpiredConnections().evictIdleConnections((long)this.properties.getConnectionMaxIdleTime(), TimeUnit.MILLISECONDS).build();
    }

    protected HttpClientConnectionManager createConnectionManager(int connectionPoolSize, int connectionTTL, int inactivityValidateTime, int socketTimeout) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager((long)connectionTTL, TimeUnit.MILLISECONDS);
        connectionManager.setMaxTotal(connectionPoolSize);
        connectionManager.setDefaultMaxPerRoute(connectionPoolSize);
        connectionManager.setValidateAfterInactivity(inactivityValidateTime);
        connectionManager.setDefaultSocketConfig(SocketConfig.copy(SocketConfig.DEFAULT).setSoTimeout(socketTimeout).build());
        log.info("Created connection manager {} with maxPoolSize = {}, maxPerRouteLimit = {}, timeToLive = {} ms", new Object[]{connectionManager.getClass(), connectionManager.getMaxTotal(), connectionManager.getDefaultMaxPerRoute(), connectionTTL});
        return connectionManager;
    }

    protected RequestConfig createRequestConfig() {
        log.info("Creating request config...");
        return RequestConfig.custom().setConnectTimeout(this.properties.getConnectionTimeout()).setSocketTimeout(this.properties.getConnectionReadTimeout()).setConnectionRequestTimeout(this.properties.getConnectionRequestTimeout()).build();
    }

    protected ConnectionKeepAliveStrategy createKeepAliveStrategy() {
        return new DefaultConnectionKeepAliveStrategy() {
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                long tm = super.getKeepAliveDuration(response, context);
                return tm == -1L ? (long)HttpClientFactory.this.properties.getDefaultKeepAliveDuration() : tm;
            }
        };
    }
}