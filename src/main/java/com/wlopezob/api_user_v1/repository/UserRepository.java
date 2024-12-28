package com.wlopezob.api_user_v1.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.wlopezob.api_user_v1.model.entity.UserEntity;

import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
  Mono<UserEntity> findByEmailAndActive(String email, boolean active);
}
