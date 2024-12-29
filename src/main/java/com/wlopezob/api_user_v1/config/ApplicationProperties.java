package com.wlopezob.api_user_v1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "http-client")
@Getter
@Setter
@Component
public class ApplicationProperties {
    private String baseUrlDataApi;
}
