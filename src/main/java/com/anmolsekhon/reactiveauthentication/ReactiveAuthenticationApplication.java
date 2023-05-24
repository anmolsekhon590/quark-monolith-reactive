package com.anmolsekhon.reactiveauthentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@SpringBootApplication
public class ReactiveAuthenticationApplication {

	@SuppressWarnings("unused")
	@Value("${quark.text-encryptor.password}")
	private String password;
	@SuppressWarnings("unused")
	@Value("${quark.text-encryptor.salt}")
	private String salt;

	public static void main(String[] args) {
		SpringApplication.run(ReactiveAuthenticationApplication.class, args);
	}

	@Bean
	public TextEncryptor textEncryptor() {
		return Encryptors.text(password, salt);
	}

}
