package com.wlopezob.api_user_v1.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiException extends RuntimeException {
  private String mensaje;
  private int httpStatus;
  private Throwable cause;

  ApiException(String mensaje, int httpStatus,
      Throwable cause) {
    super(mensaje, cause);
    this.mensaje = mensaje;
    this.httpStatus = httpStatus;
    this.cause = cause;
  }

  public ApiException buildCustom() {
    return new ApiException(this.mensaje, this.httpStatus, this.cause);
  }
}
