package com.wlopezob.api_user_v1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "validation")
@Getter
@Setter
public class ValidationConfig {
    private PasswordValidation password = new PasswordValidation();

    @Getter
    @Setter
    public static class PasswordValidation {
        private String pattern;
    }
}
