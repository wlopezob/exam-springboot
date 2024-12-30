package com.wlopezo.api_data_v1.model.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class UserDataRequest {
    @Schema(description = "The user name", example = "Juan Rodriguez", requiredMode = RequiredMode.REQUIRED)
    @NotNull(message = "El nombre no puede ser nulo")
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String name;

    @Schema(description = "The user email", example = "juan01@rodriguez.org", requiredMode = RequiredMode.REQUIRED)
    @NotNull(message = "El email no puede ser nulo")
    @NotEmpty(message = "El email no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$", 
            message = "El correo debe tener un formato válido (ejemplo@dominio.com)")
    private String email;

    @Schema(description = "The user password", requiredMode = RequiredMode.REQUIRED, example = "hunter211$")
    @NotNull(message = "El password no puede ser nula")
    @NotEmpty(message = "El password no puede estar vacía")
    private String password;

    @Schema(description = "The user phones", requiredMode = RequiredMode.REQUIRED, minLength = 1)
    @Size(min = 1, message = "Debe tener al menos un teléfono")
    @NotEmpty(message = "La lista de teléfonos no puede estar vacía")
    @Valid
    private List<PhoneRequest> phones;

    @Schema(description = "The user token", requiredMode = RequiredMode.REQUIRED, example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikp1YW4gUm9kcmlndWV6IiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    @NotNull(message = "El token no puede ser nula")
    @NotEmpty(message = "El token no puede estar vacía")
    private String token;
}
