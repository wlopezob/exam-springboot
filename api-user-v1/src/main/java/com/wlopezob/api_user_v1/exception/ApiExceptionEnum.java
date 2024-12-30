package com.wlopezob.api_user_v1.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ApiExceptionEnum {
    ER0001("ER0001",  "El correo ya registrado", HttpStatus.CONFLICT.value());

    private String code;
    private String mensaje;
    private int errorCategory;

    ApiExceptionEnum(String code, String mensaje, int errorCategory) {
        this.code = code;
        this.mensaje = mensaje;
        this.errorCategory = errorCategory;
    }

    public ApiException buildException(Throwable error) {
        ApiException apiException = new ApiException();
        apiException.setMensaje(mensaje);
        apiException.setHttpStatus(errorCategory);
        apiException.setCause(error);
        return apiException.buildCustom();
    }

    public ApiException buildException() {
        ApiException apiException = new ApiException();
        apiException.setMensaje(mensaje);
        apiException.setHttpStatus(errorCategory);
        return apiException.buildCustom();
    }

}
