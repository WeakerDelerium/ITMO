package ru.web4back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import ru.web4back.config.TokenConfig;

@SpringBootApplication
@EnableConfigurationProperties(TokenConfig.class)
public class Web4backApplication {
	public static void main(String[] args) {
		SpringApplication.run(Web4backApplication.class, args);
	}
}
