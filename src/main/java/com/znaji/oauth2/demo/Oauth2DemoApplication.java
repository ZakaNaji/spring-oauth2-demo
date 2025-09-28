package com.znaji.oauth2.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class Oauth2DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2DemoApplication.class, args);
	}

}
