package com.wlopezob.api_user_v1.apicaller;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.wlopezob.api_user_v1.config.ApplicationProperties;
import com.wlopezob.api_user_v1.thirdparty.api_data_v1.model.UserDataRequest;
import com.wlopezob.api_user_v1.thirdparty.api_data_v1.model.UserDataResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiDataCaller {
  private final WebClient.Builder webClientBuilder;
  private final ApplicationProperties applicationProperties;

  /**
   * Saves a user data request to the external API.
   *
   * @param userDataRequest The user data request object containing the information to be saved
   * @return A Mono containing the UserDataResponse with the saved user information
   * @throws WebClientResponseException if there is an error response from the API
   * @throws WebClientRequestException if there is an error making the request
   */
  public Mono<UserDataResponse> save(UserDataRequest userDataRequest) {
    return webClientBuilder.baseUrl(applicationProperties.getBaseUrlDataApi())
        //.filter(errorHandlingFilter())
        .build()
        .post()
        .uri("/user")
        .bodyValue(userDataRequest)
        .retrieve()
        .bodyToMono(UserDataResponse.class)
      .doOnNext(user -> log.info("user: {}", user))
        .doOnSuccess(user -> log.info("save() completed"))
        .doOnError(e -> log.error("Error in save()", e));
  }

}
