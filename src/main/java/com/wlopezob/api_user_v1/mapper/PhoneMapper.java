package com.wlopezob.api_user_v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.wlopezob.api_user_v1.model.entity.PhoneEntity;
import com.wlopezob.api_user_v1.model.dto.PhoneRequest;

@Mapper(componentModel = "spring")
public interface PhoneMapper {
    PhoneMapper INSTANCE = Mappers.getMapper(PhoneMapper.class);

    @Mapping(target = "phoneId", ignore = true)
    @Mapping(target = "userId", source = "userId")
    PhoneEntity toEntity(PhoneRequest phoneRequest, Long userId);
}
