package ru.web4back.dto.auth;

import jakarta.validation.constraints.*;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPayloadDTO implements AuthRequestDTO, Serializable {

    @NotNull(message = "Username is required")
    @NotBlank(message = "Username can't be empty")
    @Size(min = 3, max = 24, message = "Username must be between 3 and 24 chars")
    private String username;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password can't be empty")
    @Size(min = 8, max = 24, message = "Password must be between 3 and 24 chars")
    private String password;

}