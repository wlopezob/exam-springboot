package com.wlopezo.api_data_v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.wlopezo.api_data_v1.model.entity.TokenEntity;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    @Mapping(target = "tokenId", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "created", expression = "java(java.time.LocalDateTime.now())")
    TokenEntity toEntity(String token, Long userId);
}
