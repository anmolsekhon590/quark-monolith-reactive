package com.anmolsekhon.reactiveauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@SpringBootApplication
public class ReactiveAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveAuthenticationApplication.class, args);
	}

	@Bean
	public TextEncryptor textEncryptor() {
		return Encryptors.text("password", "5c0744940b5c369b");
	}

}
