package com.wlopezob.api_user_v1.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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

import com.wlopezob.api_user_v1.apicaller.ApiDataCaller;
import com.wlopezob.api_user_v1.config.JwtToken;
import com.wlopezob.api_user_v1.mapper.UserMapper;
import com.wlopezob.api_user_v1.model.dto.UserRequest;
import com.wlopezob.api_user_v1.model.dto.UserResponse;
import com.wlopezob.api_user_v1.service.RedisService;
import com.wlopezob.api_user_v1.thirdparty.api_data_v1.model.UserDataRequest;
import com.wlopezob.api_user_v1.thirdparty.api_data_v1.model.UserDataResponse;
import com.wlopezob.api_user_v1.util.TestProperties;
import com.wlopezob.api_user_v1.util.TestUtil;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

 
 
  @Mock
  private UserMapper userMapper;

  @Mock
  private PasswordEncoder passwordEncoder;
  @Spy
  private JwtToken jwtToken;

  @Mock
  private RedisService redisService;

  @InjectMocks
  private UserServiceImpl userService;

  @Mock ApiDataCaller apiDataCaller;
  private 

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
    
    UserDataRequest userDataRequest = TestUtil.loadJsonFromResource("mock/userDataRequest.json",
    UserDataRequest.class);

    UserDataResponse userDataResponse = TestUtil.loadJsonFromResource("mock/userDataResponse.json",
    UserDataResponse.class);

    UserResponse userResponse = TestUtil.loadJsonFromResource("mock/userResponse.json",
    UserResponse.class);

    when(passwordEncoder.encode(anyString())).thenReturn(userRequest.getPassword());
    when(userMapper.toUserDataRequest(any(UserRequest.class), anyString(), anyString()))
        .thenReturn(userDataRequest);

    when(apiDataCaller.save(any(UserDataRequest.class))).thenReturn(Mono.just(userDataResponse));
    when(userMapper.toUserResponse(any(UserDataResponse.class))).thenReturn(userResponse);
    when(redisService.save(anyString(), any())).thenReturn(Mono.just(userDataResponse));

    StepVerifier.create(userService.save(userRequest))
        .assertNext(response -> {
          Assertions.assertEquals(userResponse.getToken(), response.getToken());
        })
        .verifyComplete();
  }

}