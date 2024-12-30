package com.wlopezob.api_user_v1.model.dto;

import com.wlopezob.api_user_v1.thirdparty.api_data_v1.model.UserDataResponse;

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
  private UserDataResponse userDataResponse;
  private UserResponse userResponse;
}
