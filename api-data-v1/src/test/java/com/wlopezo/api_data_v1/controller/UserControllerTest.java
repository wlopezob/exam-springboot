package com.wlopezo.api_data_v1.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wlopezo.api_data_v1.model.dto.UserDataRequest;
import com.wlopezo.api_data_v1.model.dto.UserDataResponse;
import com.wlopezo.api_data_v1.service.UserService;
import com.wlopezo.api_data_v1.util.TestUtil;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  @Test
  @DisplayName("Save user when service working")
  void saveUserWhenServiceWorking() {
    UserDataRequest userDataRequest = TestUtil.loadJsonFromResource("mock/userDataRequest.json",
        UserDataRequest.class);
    UserDataResponse userDataResponse = TestUtil.loadJsonFromResource("mock/userDataResponse.json",
        UserDataResponse.class);

    when(userService.save(userDataRequest)).thenReturn(Mono.just(userDataResponse));
    Mono<ResponseEntity<UserDataResponse>> response = userController.save(userDataRequest);
    StepVerifier.create(response)
        .expectNext(ResponseEntity.status(HttpStatus.CREATED).body(userDataResponse))
        .verifyComplete();

  }
}