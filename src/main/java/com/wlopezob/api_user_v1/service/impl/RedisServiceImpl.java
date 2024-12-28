package com.wlopezob.api_user_v1.service.impl;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import com.wlopezob.api_user_v1.service.RedisService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Implementation of RedisService interface that provides functionality for interacting with Redis cache.
 * This service uses ReactiveRedisTemplate to perform asynchronous Redis operations.
 * The implementation supports storing and retrieving objects in Redis using reactive programming model.
 *
 * @author wlopezob
 */
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

  private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

  /**
   * Saves a value in Redis with the specified key.
   *
   * @param key The key under which the value will be stored in Redis
   * @param value The value to be stored in Redis
   * @param <T> The type of the value being stored
   * @return A Mono containing the stored value
   */
  @Override
  public <T> Mono<T> save(String key, T value) {
    return reactiveRedisTemplate.opsForValue()
        .set(key, value).then(Mono.just(value));
  }

}
