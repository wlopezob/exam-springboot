package com.wlopezo.api_data_v1.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wlopezo.api_data_v1.exception.ApiException;
import com.wlopezo.api_data_v1.exception.ApiExceptionEnum;
import com.wlopezo.api_data_v1.mapper.PhoneMapper;
import com.wlopezo.api_data_v1.mapper.TokenMapper;
import com.wlopezo.api_data_v1.mapper.UserMapper;
import com.wlopezo.api_data_v1.model.dto.PhoneRequest;
import com.wlopezo.api_data_v1.model.dto.UserDataRequest;
import com.wlopezo.api_data_v1.model.dto.UserDataResponse;
import com.wlopezo.api_data_v1.model.entity.PhoneEntity;
import com.wlopezo.api_data_v1.model.entity.TokenEntity;
import com.wlopezo.api_data_v1.model.entity.UserEntity;
import com.wlopezo.api_data_v1.repository.PhoneRepository;
import com.wlopezo.api_data_v1.repository.TokenRepository;
import com.wlopezo.api_data_v1.repository.UserRepository;
import com.wlopezo.api_data_v1.util.TestUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private PhoneRepository phoneRepository;
  @Mock
  private UserMapper userMapper;
  @Mock
  private PhoneMapper phoneMapper;

  @Mock
  private TokenRepository tokenRepository;
  @Mock
  private TokenMapper tokenMapper;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  @DisplayName("Save user when email not exist")
  void saveUserWhenEmailNotExist() {
    UserDataRequest userDataRequest = TestUtil.loadJsonFromResource("mock/userDataRequest.json",
        UserDataRequest.class);

    UserEntity userEntity = TestUtil.loadJsonFromResource("mock/userEntity.json",
        UserEntity.class);

    List<PhoneEntity> phoneEntities = Arrays.asList(TestUtil
        .loadJsonFromResource("mock/phoneEntity.json", PhoneEntity[].class));

    UserDataResponse userDataResponse = TestUtil.loadJsonFromResource("mock/userDataResponse.json",
        UserDataResponse.class);

    when(userRepository.findByEmailAndActive(anyString(), anyBoolean()))
        .thenReturn(Mono.empty());

    when(userMapper.toEntity(any(UserDataRequest.class)))
        .thenReturn(userEntity);

    when(userRepository.save(any(UserEntity.class)))
        .thenReturn(Mono.just(userEntity));

    when(phoneMapper.toEntity(any(PhoneRequest.class), anyLong()))
        .thenReturn(phoneEntities.get(0));

    when(phoneRepository.saveAll(anyList())).thenReturn(Flux.fromIterable(phoneEntities));

    TokenEntity tokenEntity = TestUtil.loadJsonFromResource("mock/tokenEntity.json",
        TokenEntity.class);

    when(phoneRepository.saveAll(anyList())).thenReturn(Flux.fromIterable(phoneEntities));
    when(tokenMapper.toEntity(anyString(), anyLong())).thenReturn(tokenEntity);

    when(tokenRepository.save(any(TokenEntity.class))).thenReturn(Mono.just(tokenEntity));

    when(userMapper.toResponse(any(UserEntity.class), anyString())).thenReturn(userDataResponse);

    StepVerifier.create(userService.save(userDataRequest))
        .assertNext(response -> {
          Assertions.assertEquals(userDataResponse.getToken(), response.getToken());
        })
        .verifyComplete();
  }

    @Test
  @DisplayName("Error save user when email exists")
  void errorSaveUserWhenWhenEmailExists() {
    UserDataRequest userDataRequest = TestUtil.loadJsonFromResource("mock/userDataRequest.json",
    UserDataRequest.class);

    UserEntity userEntity = TestUtil.loadJsonFromResource("mock/userEntity.json",
        UserEntity.class);
    when(userRepository.findByEmailAndActive(anyString(), anyBoolean()))
        .thenReturn(Mono.just(userEntity));
    when(userMapper.toEntity(any(UserDataRequest.class)))
        .thenReturn(userEntity);
    StepVerifier.create(userService.save(userDataRequest))
        .expectErrorMatches(ex -> ex instanceof ApiException && ((ApiException) ex).getMensaje()
            .equals(ApiExceptionEnum.ER0001.getMensaje()))
        .verify();
  }
}
