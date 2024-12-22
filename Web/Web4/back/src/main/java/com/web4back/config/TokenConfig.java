package ru.web4back.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import ru.web4back.exception.JWTSecretNotFoundException;

import java.time.Duration;
import java.util.Optional;

@ConfigurationProperties(prefix = "token")
public record TokenConfig(Access access, Refresh refresh) {

    @ConstructorBinding
    public TokenConfig(Access access, Refresh refresh) {
        this.access = Optional.ofNullable(access).orElseGet(() -> new Access(null, null));
        this.refresh = Optional.ofNullable(refresh).orElseGet(() -> new Refresh(null));
    }

    public record Access(String secret, Duration validity) {
        @ConstructorBinding
        public Access(String secret, Duration validity) {
            this.secret = Optional.ofNullable(secret).orElseThrow(JWTSecretNotFoundException::new);
            this.validity = Optional.ofNullable(validity).orElse(Duration.ofMinutes(30));
        }
    }

    public record Refresh(Duration validity) {
        @ConstructorBinding
        public Refresh(Duration validity) {
            this.validity = Optional.ofNullable(validity).orElse(Duration.ofHours(6));
        }
    }

}
