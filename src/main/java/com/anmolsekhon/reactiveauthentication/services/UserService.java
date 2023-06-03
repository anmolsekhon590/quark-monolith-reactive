package com.anmolsekhon.reactiveauthentication.services;

import com.anmolsekhon.reactiveauthentication.constants.Role;
import com.anmolsekhon.reactiveauthentication.models.auth.AuthRequest;
import com.anmolsekhon.reactiveauthentication.repositories.UserRepository;
import com.anmolsekhon.reactiveauthentication.security.PBKDF2Encoder;
import com.anmolsekhon.reactiveauthentication.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PBKDF2Encoder passwordEncoder;
    private static final List<Role> DEFAULT_ROLES = new ArrayList<>() {{
        this.add(Role.ROLE_USER);
    }};

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Mono<User> register(AuthRequest authRequest) {
        return userRepository.findByUsername(authRequest.getUsername()).hasElement()
                .flatMap(user -> (user) ?  Mono.error(new IllegalArgumentException("exists")) :
                        userRepository.save(new User(UUID.randomUUID().toString(), authRequest.getUsername(),
                                passwordEncoder.encode(authRequest.getPassword()), true, DEFAULT_ROLES)
                ));
    }
}
