package com.wlopezob.api_user_v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.wlopezob.api_user_v1.model.dto.UserRequest;
import com.wlopezob.api_user_v1.model.dto.UserResponse;
import com.wlopezob.api_user_v1.thirdparty.api_data_v1.model.UserDataRequest;
import com.wlopezob.api_user_v1.thirdparty.api_data_v1.model.UserDataResponse;
import com.wlopezob.api_user_v1.util.Util;

@Mapper(componentModel = "spring", imports = {Util.class})
public interface UserMapper {
    
    @Mapping(target = "password", source = "password")
    @Mapping(target = "token", source = "token")
    UserDataRequest toUserDataRequest(UserRequest userRequest, String password, String token);

    UserResponse toUserResponse(UserDataResponse userDataResponse);
}
