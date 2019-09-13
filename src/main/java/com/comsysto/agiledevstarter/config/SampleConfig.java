package com.comsysto.agiledevstarter.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConditionalOnCloudPlatform(CloudPlatform.CLOUD_FOUNDRY)
@ConfigurationProperties("agile-dev-starter.sample-config")
public class SampleConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleConfig.class);

    private String url;
    private String user;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @PostConstruct
    public void showSampleConfig() {
        LOGGER.info("Sample Config url: {}", url);
        LOGGER.info("Sample Config user: {}", user);
        LOGGER.info("Sample Config password: {}", password);
    }
}
