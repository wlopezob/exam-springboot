package com.wlopezob.api_user_v1.service.impl;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import com.wlopezob.api_user_v1.service.RedisService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

  private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

  @Override
  public <T> Mono<T> save(String key, T value) {
    return reactiveRedisTemplate.opsForValue()
        .set(key, value).then(Mono.just(value));
  }

}
