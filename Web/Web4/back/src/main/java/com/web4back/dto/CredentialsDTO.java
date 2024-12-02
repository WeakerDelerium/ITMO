package com.web4back.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredentialsDTO implements Serializable {
    private String accessToken;
    private String refreshToken;
}
