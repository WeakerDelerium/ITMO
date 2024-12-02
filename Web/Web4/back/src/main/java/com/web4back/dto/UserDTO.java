package com.web4back.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {

    @NotNull(message = "Login can't be null")
    @Size(max = 24, message = "Login length must be less than 24 chars")
    private String login;

    @NotNull(message = "Password can't be null")
    @Size(max = 24, message = "Password length must be less than 24 chars")
    private String password;

}