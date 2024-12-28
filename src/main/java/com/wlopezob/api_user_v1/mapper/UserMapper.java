package com.wlopezob.api_user_v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.wlopezob.api_user_v1.model.entity.UserEntity;
import com.wlopezob.api_user_v1.util.Util;
import com.wlopezob.api_user_v1.model.dto.UserRequest;
import com.wlopezob.api_user_v1.model.dto.UserResponse;

@Mapper(componentModel = "spring", imports = {Util.class})
public interface UserMapper {
    
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "created", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "modified", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "lastLogin", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "password", source = "password")
    UserEntity toEntity(UserRequest userRequest, String password);

    @Mapping(target = "created", expression = "java(Util.formatDate(userEntity.getCreated()))")
    @Mapping(target = "modified", expression = "java(Util.formatDate(userEntity.getModified()))")
    @Mapping(target = "lastLogin", expression = "java(Util.formatDate(userEntity.getLastLogin()))")
    @Mapping(target = "token", source = "token")
    UserResponse toResponse(UserEntity userEntity, String token);
}
