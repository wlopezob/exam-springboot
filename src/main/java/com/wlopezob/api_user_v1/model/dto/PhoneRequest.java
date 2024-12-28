package com.wlopezob.api_user_v1.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneRequest {
    @Schema(description = "The phone number", example = "1234567", required = true)
    @NotNull(message = "El número de teléfono no puede ser nulo")
    @NotEmpty(message = "El número de teléfono no puede estar vacío")
    private String number;

    @Schema(description = "The city code", example = "1", required = true)
    @NotNull(message = "El código de ciudad del teléfono no puede ser nulo")
    @NotEmpty(message = "El código de ciudad del teléfono no puede estar vacío")
    @JsonProperty("citycode")
    private String cityCode;

    @Schema(description = "The country code", example = "57", required = true)
    @NotNull(message = "El código de país del teléfono no puede ser nulo")
    @NotEmpty(message = "El código de país del teléfono no puede estar vacío")
    @JsonProperty("contrycode")
    private String countryCode;
}
