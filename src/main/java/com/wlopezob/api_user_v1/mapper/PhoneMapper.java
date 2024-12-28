package com.wlopezob.api_user_v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.wlopezob.api_user_v1.model.dto.PhoneRequest;
import com.wlopezob.api_user_v1.model.entity.PhoneEntity;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "phoneId", ignore = true)
    PhoneEntity toEntity(PhoneRequest phoneRequest, Long userId);
}
