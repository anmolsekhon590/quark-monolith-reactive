package com.anmolsekhon.reactiveauthentication.services;

import com.anmolsekhon.reactiveauthentication.constants.Role;
import com.anmolsekhon.reactiveauthentication.entities.User;
import com.anmolsekhon.reactiveauthentication.models.Friend;
import com.anmolsekhon.reactiveauthentication.models.auth.AuthRequest;
import com.anmolsekhon.reactiveauthentication.repositories.UserRepository;
import com.anmolsekhon.reactiveauthentication.security.PBKDF2Encoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
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
                                passwordEncoder.encode(authRequest.getPassword()), true, DEFAULT_ROLES,
                                new ArrayList<>(), new ArrayList<>())
                ));
    }

    public Mono<Void> addFriend(String username, Friend friend) {
        return userRepository.findByUsername(username)
                .doOnNext(user -> user.getFriends().add(friend.username()))
                .flatMap(userRepository::save)
                .doOnError(error -> log.error(error.getMessage()))
                .then()
                .cast(Void.class);
    }

    public Mono<?> getAllFriends(String username) {
        return userRepository.findFriendsByUsername(username);
    }
}
