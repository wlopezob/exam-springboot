package com.wlopezob.api_user_v1.model.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    private Long userId;
    private String name;
    private String email;
    private String password;
    private boolean active;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;

}
