package com.web4back.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Principal;

@Getter
@AllArgsConstructor
public class UserPrincipals implements Principal {
    private final Long userId;
    private final String token;

    @Override
    public String getName() {
        return userId.toString();
    }
}
