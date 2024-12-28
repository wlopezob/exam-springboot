package com.wlopezob.api_user_v1.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.wlopezob.api_user_v1.model.entity.PhoneEntity;

public interface PhoneRepository extends R2dbcRepository<PhoneEntity, String> {
}
