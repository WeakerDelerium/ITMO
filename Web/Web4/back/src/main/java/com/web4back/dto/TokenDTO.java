package com.web4back.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO implements Serializable {
    @NotNull(message = "Token cannot be null")
    private String token;
}
