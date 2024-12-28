package com.wlopezob.api_user_v1.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class CustomControllerAdvice {
  @ExceptionHandler(ApiException.class)
  public Mono<ResponseEntity<DataApiException>> handlerApiException(ApiException ex) {
    log.error("{}", ExceptionUtils.getStackTrace(ex));
    DataApiException dataApiException = returnDataApiException(ex);
    return Mono.just(new ResponseEntity<DataApiException>(dataApiException,
        HttpStatus.valueOf(dataApiException.getHttpStatus())));
  }

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<DataApiException>> handlerApiException(Exception ex) {
    String stackTrace = ExceptionUtils.getStackTrace(ex);
    log.error("{}", stackTrace);
    DataApiException dataApiException = DataApiException.builder()
        .mensaje(String.format("%s, %s", ex.getMessage(), stackTrace))
        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .build();
    return Mono.just(new ResponseEntity<DataApiException>(dataApiException,
        HttpStatus.valueOf(dataApiException.getHttpStatus())));
  }

  @ExceptionHandler(WebExchangeBindException.class)
  public Mono<ResponseEntity<DataApiException>> handleValidationExceptions(WebExchangeBindException ex) {
    List<String> errors = new ArrayList<String>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String errorMessage = error.getDefaultMessage();
      errors.add(errorMessage);
    });
    DataApiException dataApiException = DataApiException.builder()
        .mensaje(String.join(", ", errors))
        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .build();
    return Mono.just(ResponseEntity.badRequest().body(dataApiException));
  }

  private DataApiException returnDataApiException(
      ApiException apiException) {
    return DataApiException.builder()
        .mensaje(apiException.getMensaje())
        .httpStatus(apiException.getHttpStatus())
        .build();
  }
}
