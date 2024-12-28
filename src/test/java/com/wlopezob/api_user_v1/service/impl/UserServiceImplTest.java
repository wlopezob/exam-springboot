package com.wlopezob.api_user_v1.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.wlopezob.api_user_v1.config.JwtToken;
import com.wlopezob.api_user_v1.exception.ApiException;
import com.wlopezob.api_user_v1.exception.ApiExceptionEnum;
import com.wlopezob.api_user_v1.mapper.PhoneMapper;
import com.wlopezob.api_user_v1.mapper.TokenMapper;
import com.wlopezob.api_user_v1.mapper.UserMapper;
import com.wlopezob.api_user_v1.model.dto.PhoneRequest;
import com.wlopezob.api_user_v1.model.dto.UserRequest;
import com.wlopezob.api_user_v1.model.dto.UserResponse;
import com.wlopezob.api_user_v1.model.entity.PhoneEntity;
import com.wlopezob.api_user_v1.model.entity.TokenEntity;
import com.wlopezob.api_user_v1.model.entity.UserEntity;
import com.wlopezob.api_user_v1.repository.PhoneRepository;
import com.wlopezob.api_user_v1.repository.TokenRepository;
import com.wlopezob.api_user_v1.repository.UserRepository;
import com.wlopezob.api_user_v1.service.RedisService;
import com.wlopezob.api_user_v1.util.TestProperties;
import com.wlopezob.api_user_v1.util.TestUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private PhoneRepository phoneRepository;
  @Mock
  private UserMapper userMapper;
  @Mock
  private PhoneMapper phoneMapper;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Spy
  private JwtToken jwtToken;
  @Mock
  private TokenRepository tokenRepository;
  @Mock
  private TokenMapper tokenMapper;
  @Mock
  private RedisService redisService;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    TestProperties testProperties = TestUtil.loadJsonFromResource("mock/testProperties.json",
        TestProperties.class);
    ReflectionTestUtils.setField(jwtToken, "secret", testProperties.getSecret());
    ReflectionTestUtils.setField(jwtToken, "expiration", testProperties.getExpiration());
    ReflectionTestUtils.setField(jwtToken, "rol", testProperties.getRol());
  }

  @Test
  @DisplayName("Save user when email not exist")
  void saveUserWhenEmailNotExist() {
    UserRequest userRequest = TestUtil.loadJsonFromResource("mock/userRequest.json",
        UserRequest.class);

    UserEntity userEntity = TestUtil.loadJsonFromResource("mock/userEntity.json",
        UserEntity.class);

    TokenEntity tokenEntity = TestUtil.loadJsonFromResource("mock/tokenEntity.json",
        TokenEntity.class);

    List<PhoneEntity> phoneEntities = Arrays.asList(TestUtil
        .loadJsonFromResource("mock/phoneEntity.json", PhoneEntity[].class));

    UserResponse userResponseService = TestUtil.loadJsonFromResource("mock/userResponseService.json",
        UserResponse.class);

    UserResponse userResponse = TestUtil.loadJsonFromResource("mock/userResponse.json",
        UserResponse.class);

    when(userRepository.findByEmailAndActive(anyString(), anyBoolean()))
        .thenReturn(Mono.empty());

    when(passwordEncoder.encode(anyString())).thenReturn(userEntity.getPassword());

    when(userMapper.toEntity(any(UserRequest.class), anyString()))
        .thenReturn(userEntity);

    when(userRepository.save(any(UserEntity.class)))
        .thenReturn(Mono.just(userEntity));

    when(phoneMapper.toEntity(any(PhoneRequest.class), anyLong()))
        .thenReturn(phoneEntities.get(0));

    when(phoneRepository.saveAll(anyList())).thenReturn(Flux.fromIterable(phoneEntities));

    when(tokenRepository.save(any(TokenEntity.class))).thenReturn(Mono.just(tokenEntity));

    when(tokenMapper.toEntity(anyString(), anyLong())).thenReturn(tokenEntity);

    when(userMapper.toResponse(any(UserEntity.class), anyString())).thenReturn(userResponseService);

    when(redisService.save(anyString(), any())).thenReturn(Mono.just(userResponse));

    StepVerifier.create(userService.save(userRequest))
        .assertNext(response -> {
          Assertions.assertEquals(userResponse.getToken(), response.getToken());
        })
        .verifyComplete();
  }

  @Test
  @DisplayName("Error save user when email exists")
  void errorSaveUserWhenWhenEmailExists() {
    UserRequest userRequest = TestUtil.loadJsonFromResource("mock/userRequest.json",
        UserRequest.class);

    UserEntity userEntity = TestUtil.loadJsonFromResource("mock/userEntity.json",
        UserEntity.class);
    when(userRepository.findByEmailAndActive(anyString(), anyBoolean()))
        .thenReturn(Mono.just(userEntity));
    when(userMapper.toEntity(any(UserRequest.class), anyString()))
        .thenReturn(userEntity);
    when(passwordEncoder.encode(anyString())).thenReturn(userEntity.getPassword());
    StepVerifier.create(userService.save(userRequest))
        .expectErrorMatches(ex -> ex instanceof ApiException && ((ApiException) ex).getMensaje()
            .equals(ApiExceptionEnum.ER0001.getMensaje()))
        .verify();
  }
}