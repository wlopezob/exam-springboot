package com.wlopezob.api_user_v1.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserResponse {
    private String userId;
    private String name;
    private String email;
    private boolean active;
    private String created;
    private String modified;
    @JsonProperty("last_login")
    private String lastLogin;
    private String token;
}
