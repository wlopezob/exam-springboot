package com.wlopezo.api_data_v1.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.wlopezo.api_data_v1.model.entity.TokenEntity;

public interface TokenRepository extends R2dbcRepository<TokenEntity, Long> {
}
