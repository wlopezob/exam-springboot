package com.wlopezob.api_user_v1.model.dto;

import com.wlopezob.api_user_v1.model.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMemory {
  private UserEntity userEntity;
  private UserResponse userResponse;
}
