package com.wlopezob.api_user_v1.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    @Schema(description = "The user id", example = "9075f560-7afb-4320-bc7a-5670a46fb51e")
    private String userId;

    @Schema(description = "The user name", example = "Juan Rodriguez")
    private String name;

    @Schema(description = "The user email", example = "juan01@rodriguez.org")
    private String email;

    @Schema(description = "The user active status", example = "true")
    private boolean active;

    @Schema(description = "The user created date", example = "27-12-2024")
    private String created;

    @Schema(description = "The user modified date", example = "27-12-2024")
    private String modified;

    @Schema(description = "The user last login date", example = "27-12-2024")
    @JsonProperty("last_login")
    private String lastLogin;

    @Schema(description = "The user token", example="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuMDFAcm9kcmlndWV6Lm9yZyIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJBRE1JTiJ9XSwiaWF0IjoxNzM1MzU2NTI0LCJleHAiOjE3MzU0NDI5MjR9.Slq7hUJYKv547jZ1ZELEPDOUlyKKu-hPsID6sR3h0VaILSUmvR49YcQJ4v4ZDNEDnvW6sqTqJ9fdXfgICqDazA")
    private String token;
}
