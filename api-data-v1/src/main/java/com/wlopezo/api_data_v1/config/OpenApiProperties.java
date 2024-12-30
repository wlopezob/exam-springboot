package com.wlopezo.api_data_v1.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("springdoc.info")
@Getter
@Setter
public class OpenApiProperties {
    private String version;
    private String title;
    private String description;
    private List<String> servers;
}