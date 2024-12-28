package com.wlopezob.api_user_v1.service;

import reactor.core.publisher.Mono;

public interface RedisService {
    <T> Mono<T> save(String key, T value);
}
