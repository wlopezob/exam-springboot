package com.wlopezob.api_user_v1.service;

import com.wlopezob.api_user_v1.model.dto.UserRequest;
import com.wlopezob.api_user_v1.model.dto.UserResponse;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserResponse> save(UserRequest userRequest); 
}
