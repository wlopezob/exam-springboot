package com.wlopezob.api_user_v1.model.dto;

import java.util.List;

import com.wlopezob.api_user_v1.validation.PasswordAnnotation;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
public class UserRequest {
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

    @Schema(description = "The user password", requiredMode = RequiredMode.REQUIRED)
    @NotNull(message = "El password no puede ser nula")
    @NotEmpty(message = "El password no puede estar vacía")
    @PasswordAnnotation(message = "El password no cumple con las politicas de seguridad")
    private String password;

    @Schema(description = "The user phones", requiredMode = RequiredMode.REQUIRED, minLength = 1)
    @Size(min = 1, message = "Debe tener al menos un teléfono")
    @NotEmpty(message = "La lista de teléfonos no puede estar vacía")
    @Valid
    private List<PhoneRequest> phones;
}
