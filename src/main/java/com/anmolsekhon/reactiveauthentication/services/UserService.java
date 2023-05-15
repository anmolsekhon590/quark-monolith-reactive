package com.anmolsekhon.reactiveauthentication.services;

import com.anmolsekhon.reactiveauthentication.repositories.UserRepository;
import com.anmolsekhon.reactiveauthentication.security.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
