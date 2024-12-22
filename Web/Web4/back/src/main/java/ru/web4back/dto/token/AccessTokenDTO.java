package ru.web4back.dto.token;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenDTO {
    private String token;
}
