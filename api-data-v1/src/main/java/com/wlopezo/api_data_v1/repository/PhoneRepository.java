package com.wlopezo.api_data_v1.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.wlopezo.api_data_v1.model.entity.PhoneEntity;


public interface PhoneRepository extends R2dbcRepository<PhoneEntity, Long> {
}