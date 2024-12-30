package com.wlopezob.api_user_v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.wlopezob.api_user_v1.exception.ApiException;
import com.wlopezob.api_user_v1.exception.DataApiException;

import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
    /**
     * Creates and configures a WebClient.Builder bean for making HTTP requests.
     * This builder includes error handling and configures memory limits for responses.
     *
     * @return WebClient.Builder configured with error handling and 16MB memory limit
     * for response processing
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
            .filter(errorHandlingFilter())
            .exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build());
    }

    /**
     * Creates an ExchangeFilterFunction that handles error responses from HTTP requests.
     * 
     * This filter processes responses from WebClient calls and transforms error responses 
     * into appropriate exceptions. When a response has an error status code:
     * - If the response body can be mapped to DataApiException, it creates an ApiException with the error message
     * - If there's a WebClientResponseException, it wraps it in an ApiException
     * 
     * @return ExchangeFilterFunction that processes error responses
     * @throws ApiException when an error response is received, containing the error message and HTTP status code
     */
    private ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
          if (clientResponse.statusCode().isError()) {
            return clientResponse.bodyToMono(DataApiException.class)
                .flatMap(dataError -> Mono.<ClientResponse>error(
                    new ApiException(dataError.getMensaje(),
                        clientResponse.statusCode().value(),
                        null)))
                .onErrorResume(WebClientResponseException.class, e -> Mono.<ClientResponse>error(new ApiException(
                    e.getMessage(),
                    clientResponse.statusCode().value(),
                    e)));
    
          }
          return Mono.just(clientResponse);
        });
      }
}
