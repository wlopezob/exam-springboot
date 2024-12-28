package com.wlopezob.api_user_v1.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wlopezob.api_user_v1.model.dto.UserRequest;
import com.wlopezob.api_user_v1.model.dto.UserResponse;
import com.wlopezob.api_user_v1.service.UserService;
import com.wlopezob.api_user_v1.util.TestUtil;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  @Test
  @DisplayName("Save user when service working")
  void saveUserWhenServiceWorking() {
    UserRequest userRequest = TestUtil.loadJsonFromResource("mock/userRequest.json", 
    UserRequest.class); 
    UserResponse userResponse = TestUtil.loadJsonFromResource("mock/userResponse.json", 
    UserResponse.class); 

    when(userService.save(userRequest)).thenReturn(Mono.just(userResponse));
    Mono<ResponseEntity<UserResponse>> response = userController.save(userRequest);
    StepVerifier.create(response)
      .expectNext(ResponseEntity.status(HttpStatus.CREATED).body(userResponse))
      .verifyComplete();

  }
}
