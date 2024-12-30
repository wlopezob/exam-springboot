package com.wlopezo.api_data_v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.wlopezo.api_data_v1.model.dto.UserDataRequest;
import com.wlopezo.api_data_v1.model.dto.UserDataResponse;
import com.wlopezo.api_data_v1.model.entity.UserEntity;
import com.wlopezo.api_data_v1.util.Util;

@Mapper(componentModel = "spring", imports = {Util.class})
public interface UserMapper {
    
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "created", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "modified", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "lastLogin", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "active", constant = "true")
    UserEntity toEntity(UserDataRequest userRequest);

    @Mapping(target = "created", expression = "java(Util.formatDate(userEntity.getCreated()))")
    @Mapping(target = "modified", expression = "java(Util.formatDate(userEntity.getModified()))")
    @Mapping(target = "lastLogin", expression = "java(Util.formatDate(userEntity.getLastLogin()))")
    @Mapping(target = "token", source = "token")
    UserDataResponse toResponse(UserEntity userEntity, String token);
}
