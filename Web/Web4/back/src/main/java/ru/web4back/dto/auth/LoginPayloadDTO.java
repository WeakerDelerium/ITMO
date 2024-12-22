package ru.web4back.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginPayloadDTO implements AuthRequestDTO, Serializable {

    @NotNull(message = "Username is required")
    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password can't be empty")
    private String password;

}
